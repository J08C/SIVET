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

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio relacionada con las ventas
public class VentaService {

    private final VentaDAO ventaDAO;
    private final ProductoDAO productoDAO;
    private final InventarioService inventarioService;

    public VentaService(
            VentaDAO ventaDAO,
            ProductoDAO productoDAO) {

        this.ventaDAO = ventaDAO;
        this.productoDAO = productoDAO;
        this.inventarioService = null;
    }

    public VentaService(
            VentaDAO ventaDAO,
            ProductoDAO productoDAO,
            InventarioService inventarioService) {

        this.ventaDAO = ventaDAO;
        this.productoDAO = productoDAO;
        this.inventarioService = inventarioService;
    }

    // Verifica si existe stock suficiente
    public boolean puedeRealizarVenta(
            int idProducto,
            int cantidadVenta) {

        if (idProducto <= 0) return false;
        if (cantidadVenta <= 0) return false;
        if (productoDAO == null) return false;

        Producto producto =
                productoDAO.buscarPorId(idProducto);

        if (producto == null) {
            return false;
        }

        return cantidadVenta <= producto.getStockActual();
    }

    // Registrar una venta simple
    public boolean registrarVenta(
            Venta venta) {

        if (venta == null) return false;
        if (venta.getTotal() < 0) return false;
        if (venta.getIdUsuario() <= 0) return false;
        if (ventaDAO == null) return false;

        ventaDAO.guardar(venta);

        return true;
    }

    // Registrar venta, detalle, descontar stock
    // y registrar salida utilizando una única transacción.
    public boolean registrarVentaConSalida(
            Venta venta,
            int idProducto,
            int cantidad,
            String motivo) {

        if (venta == null) return false;
        if (venta.getTotal() < 0) return false;
        if (venta.getIdUsuario() <= 0) return false;
        if (idProducto <= 0) return false;
        if (cantidad <= 0) return false;
        if (motivo == null || motivo.trim().isEmpty()) {
            return false;
        }

        if (ventaDAO == null) return false;
        if (productoDAO == null) return false;

        // Verificar stock antes de iniciar la transacción
        if (!puedeRealizarVenta(
                idProducto,
                cantidad)) {

            return false;
        }

        Connection conexion = null;

        try {

            // Abrir conexión transaccional
            conexion =
                    ConexionDB.obtenerConexionTransaccion();

            // DAO concretos preparados para compartir
            // la misma conexión.
            VentaDAOImpl ventaDAOImpl =
                    (VentaDAOImpl) ventaDAO;

            ProductoDAOImpl productoDAOImpl =
                    (ProductoDAOImpl) productoDAO;

            DetalleVentaDAOImpl detalleVentaDAOImpl =
                    new DetalleVentaDAOImpl();

            MovimientoInventarioDAOImpl movimientoDAOImpl =
                    new MovimientoInventarioDAOImpl();

            // 1. Registrar la cabecera de la venta
            ventaDAOImpl.guardar(
                    venta,
                    conexion);

            // 2. Obtener producto usando
            // la misma conexión
            Producto producto =
                    productoDAOImpl.buscarPorId(
                            idProducto,
                            conexion);

            if (producto == null) {
                throw new RuntimeException(
                        "El producto no existe.");
            }

            // 3. Verificar nuevamente el stock
            // dentro de la transacción
            if (cantidad > producto.getStockActual()) {
                throw new RuntimeException(
                        "Stock insuficiente.");
            }

            // 4. Calcular subtotal
            double precioUnitario =
                    producto.getPrecioVenta();

            double subtotal =
                    precioUnitario * cantidad;

            // 5. Registrar detalle de la venta
            DetalleVenta detalle =
                    new DetalleVenta();

            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(
                    precioUnitario);
            detalle.setSubtotal(subtotal);
            detalle.setIdVenta(
                    venta.getIdVenta());
            detalle.setIdProducto(
                    idProducto);

            detalleVentaDAOImpl.guardar(
                    detalle,
                    conexion);

            // 6. Descontar stock
            int nuevoStock =
                    producto.getStockActual()
                            - cantidad;

            producto.setStockActual(
                    nuevoStock);

            boolean actualizado =
                    productoDAOImpl.actualizar(
                            producto,
                            conexion);

            if (!actualizado) {
                throw new RuntimeException(
                        "No se pudo actualizar el stock.");
            }

            // 7. Registrar movimiento SALIDA
            MovimientoInventario movimiento =
                    new MovimientoInventario();

            movimiento.setFecha(
                    LocalDateTime.now());

            movimiento.setTipo("SALIDA");

            movimiento.setCantidad(
                    cantidad);

            movimiento.setSaldo(
                    nuevoStock);

            movimiento.setMotivo(
                    motivo);

            movimiento.setIdProducto(
                    idProducto);

            movimiento.setIdUsuario(
                    venta.getIdUsuario());

            movimientoDAOImpl.guardar(
                    movimiento,
                    conexion);

            // 8. Todo salió correctamente
            conexion.commit();

            return true;

        } catch (Exception e) {

            // Si cualquier paso falla,
            // deshacemos toda la operación.
            if (conexion != null) {

                try {
                    conexion.rollback();
                } catch (Exception rollbackError) {

                    System.out.println(
                            "Error al realizar rollback: "
                                    + rollbackError.getMessage());
                }
            }

            System.out.println(
                    "Transacción de venta cancelada: "
                            + e.getMessage());

            return false;

        } finally {

            if (conexion != null) {

                try {
                    conexion.close();
                } catch (Exception e) {

                    System.out.println(
                            "Error al cerrar conexión: "
                                    + e.getMessage());
                }
            }
        }
    }

    // Listar ventas
    public List<Venta> listarVentas() {

        if (ventaDAO == null) {
            return new ArrayList<>();
        }

        return ventaDAO.listar();
    }

    // Buscar venta
    public Venta buscarVentaPorId(
            int idVenta) {

        if (idVenta <= 0) return null;

        return ventaDAO.buscarPorId(idVenta);
    }

    // Actualizar venta
    public boolean actualizarVenta(
            Venta venta) {

        if (venta == null) return false;
        if (venta.getIdVenta() <= 0) return false;
        if (venta.getTotal() < 0) return false;
        if (venta.getIdUsuario() <= 0) return false;

        Venta existente =
                ventaDAO.buscarPorId(
                        venta.getIdVenta());

        if (existente == null) {
            return false;
        }

        ventaDAO.actualizar(venta);

        return true;
    }

    // Eliminar venta
    public boolean eliminarVenta(
            int idVenta) {

        if (idVenta <= 0) return false;

        Venta existente =
                ventaDAO.buscarPorId(idVenta);

        if (existente == null) {
            return false;
        }

        ventaDAO.eliminar(idVenta);

        return true;
    }
}