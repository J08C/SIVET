
package com.sivet.service;

import com.sivet.dao.ConexionDB;
import com.sivet.dao.DetalleVentaDAOImpl;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.VentaDAOImpl;
import com.sivet.dao.VentaDAO;
import com.sivet.model.DetalleVenta;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;
import com.sivet.model.Venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final VentaDAO ventaDAO;
    private final ProductoDAO productoDAO;
    private final DetalleVentaDAOImpl detalleVentaDAO;
    private final MovimientoInventarioDAOImpl movimientoInventarioDAO;
    private final InventarioService inventarioService;

    // Constructor utilizado por Spring: inyección de dependencias.
    @Autowired
    public VentaService(
            VentaDAO ventaDAO,
            ProductoDAO productoDAO,
            DetalleVentaDAOImpl detalleVentaDAO,
            MovimientoInventarioDAOImpl movimientoInventarioDAO) {

        this.ventaDAO = ventaDAO;
        this.productoDAO = productoDAO;
        this.detalleVentaDAO = detalleVentaDAO;
        this.movimientoInventarioDAO = movimientoInventarioDAO;
        this.inventarioService = null;
    }

    // Constructor conservado por compatibilidad con código existente.
    public VentaService(
            VentaDAO ventaDAO,
            ProductoDAO productoDAO) {

        this(
                ventaDAO,
                productoDAO,
                new DetalleVentaDAOImpl(),
                new MovimientoInventarioDAOImpl()
        );
    }

    // Se conserva porque lo utiliza VentaServiceTransaccionTest.
    public VentaService(
            VentaDAO ventaDAO,
            ProductoDAO productoDAO,
            InventarioService inventarioService) {

        this(
                ventaDAO,
                productoDAO,
                new DetalleVentaDAOImpl(),
                new MovimientoInventarioDAOImpl()
        );
    }

    public boolean puedeRealizarVenta(
            int idProducto,
            int cantidadVenta) {

        if (idProducto <= 0 || cantidadVenta <= 0) {
            return false;
        }

        Producto producto = productoDAO.buscarPorId(idProducto);

        return producto != null
                && cantidadVenta <= producto.getStockActual();
    }

    public boolean registrarVenta(Venta venta) {

        if (venta == null
                || venta.getTotal() < 0
                || venta.getIdUsuario() <= 0) {
            return false;
        }

        ventaDAO.guardar(venta);
        return true;
    }

    public boolean registrarVentaConSalida(
            Venta venta,
            int idProducto,
            int cantidad,
            String motivo) {

        if (venta == null
                || venta.getTotal() < 0
                || venta.getIdUsuario() <= 0
                || idProducto <= 0
                || cantidad <= 0
                || motivo == null
                || motivo.trim().isEmpty()) {
            return false;
        }

        if (!puedeRealizarVenta(idProducto, cantidad)) {
            return false;
        }

        Connection conexion = null;

        try {
            conexion = ConexionDB.obtenerConexionTransaccion();

            VentaDAOImpl ventaDAOImpl = (VentaDAOImpl) ventaDAO;
            ProductoDAOImpl productoDAOImpl = (ProductoDAOImpl) productoDAO;

            // 1. Registrar la cabecera de la venta.
            ventaDAOImpl.guardar(venta, conexion);

            // 2. Consultar el producto con la misma conexión.
            Producto producto = productoDAOImpl.buscarPorId(
                    idProducto,
                    conexion
            );

            if (producto == null) {
                throw new IllegalStateException("El producto no existe.");
            }

            // 3. Comprobar nuevamente el stock dentro de la transacción.
            if (cantidad > producto.getStockActual()) {
                throw new IllegalStateException("Stock insuficiente.");
            }

            // 4. Calcular el subtotal.
            double precioUnitario = producto.getPrecioVenta();
            double subtotal = precioUnitario * cantidad;

            // 5. Guardar el detalle de venta.
            DetalleVenta detalle = new DetalleVenta();
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(idProducto);

            detalleVentaDAO.guardar(detalle, conexion);

            // 6. Descontar el stock.
            int nuevoStock = producto.getStockActual() - cantidad;
            producto.setStockActual(nuevoStock);

            boolean actualizado = productoDAOImpl.actualizar(
                    producto,
                    conexion
            );

            if (!actualizado) {
                throw new IllegalStateException(
                        "No se pudo actualizar el stock."
                );
            }

            // 7. Registrar la salida de inventario.
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipo("SALIDA");
            movimiento.setCantidad(cantidad);
            movimiento.setSaldo(nuevoStock);
            movimiento.setMotivo(motivo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(venta.getIdUsuario());

            movimientoInventarioDAO.guardar(movimiento, conexion);

            // 8. Confirmar todas las operaciones juntas.
            conexion.commit();
            return true;

        } catch (Exception e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (Exception rollbackError) {
                    System.err.println(
                            "Error al deshacer la transacción: "
                                    + rollbackError.getMessage()
                    );
                }
            }

            System.err.println(
                    "Transacción de venta cancelada: "
                            + e.getMessage()
            );

            return false;

        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.err.println(
                            "Error al cerrar la conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }

    public List<Venta> listarVentas() {
        return ventaDAO == null
                ? new ArrayList<>()
                : ventaDAO.listar();
    }

    public Venta buscarVentaPorId(int idVenta) {
        if (idVenta <= 0) {
            return null;
        }

        return ventaDAO.buscarPorId(idVenta);
    }

    public boolean actualizarVenta(Venta venta) {
        if (venta == null
                || venta.getIdVenta() <= 0
                || venta.getTotal() < 0
                || venta.getIdUsuario() <= 0) {
            return false;
        }

        Venta existente = ventaDAO.buscarPorId(venta.getIdVenta());

        if (existente == null) {
            return false;
        }

        ventaDAO.actualizar(venta);
        return true;
    }

    public boolean eliminarVenta(int idVenta) {
        if (idVenta <= 0) {
            return false;
        }

        Venta existente = ventaDAO.buscarPorId(idVenta);

        if (existente == null) {
            return false;
        }

        ventaDAO.eliminar(idVenta);
        return true;
    }
}
