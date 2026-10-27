
package com.sivet.service;

import com.sivet.dao.ConexionDB;
import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDateTime;

// Contiene la lógica de negocio relacionada con el inventario
@Service
public class InventarioService {

    private final MovimientoInventarioDAO movimientoDAO;
    private final ProductoDAO productoDAO;

    // Inyección de dependencias
    public InventarioService(
            MovimientoInventarioDAO movimientoDAO,
            ProductoDAO productoDAO) {

        this.movimientoDAO = movimientoDAO;
        this.productoDAO = productoDAO;
    }

    // Registrar un movimiento de inventario
    public boolean registrarMovimiento(
            MovimientoInventario movimiento) {

        if (movimiento == null
                || movimiento.getCantidad() <= 0
                || movimiento.getTipo() == null
                || (!movimiento.getTipo().equals("ENTRADA")
                && !movimiento.getTipo().equals("SALIDA"))
                || movimiento.getSaldo() < 0
                || movimiento.getIdProducto() <= 0
                || movimiento.getIdUsuario() <= 0) {
            return false;
        }

        if (movimientoDAO == null) {
            return false;
        }

        movimientoDAO.guardar(movimiento);
        return true;
    }

    // Registrar una entrada de productos
    public boolean registrarEntrada(
            int idProducto,
            int cantidad,
            int idUsuario,
            String motivo) {

        if (idProducto <= 0 || cantidad <= 0 || idUsuario <= 0
                || motivo == null || motivo.trim().isEmpty()) {
            return false;
        }

        if (productoDAO == null || movimientoDAO == null) {
            return false;
        }

        /*
         * Si usamos los DAO reales, utilizamos una única transacción.
         */
        if (productoDAO instanceof ProductoDAOImpl
                && movimientoDAO instanceof MovimientoInventarioDAOImpl) {

            return registrarEntradaConTransaccion(
                    idProducto, cantidad, idUsuario, motivo);
        }

        // Lógica para DAO simulados y pruebas unitarias
        try {
            Producto producto = productoDAO.buscarPorId(idProducto);

            if (producto == null) {
                return false;
            }

            int nuevoStock = producto.getStockActual() + cantidad;
            producto.setStockActual(nuevoStock);

            if (!productoDAO.actualizar(producto)) {
                return false;
            }

            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipo("ENTRADA");
            movimiento.setCantidad(cantidad);
            movimiento.setSaldo(nuevoStock);
            movimiento.setMotivo(motivo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(idUsuario);

            movimientoDAO.guardar(movimiento);
            return true;

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar entrada: " + e.getMessage());
            return false;
        }
    }

    // Registrar una salida de productos
    public boolean registrarSalida(
            int idProducto,
            int cantidad,
            int idUsuario,
            String motivo) {

        if (idProducto <= 0 || cantidad <= 0 || idUsuario <= 0
                || motivo == null || motivo.trim().isEmpty()) {
            return false;
        }

        if (productoDAO == null || movimientoDAO == null) {
            return false;
        }

        // DAO reales: producto y movimiento en una transacción
        if (productoDAO instanceof ProductoDAOImpl
                && movimientoDAO instanceof MovimientoInventarioDAOImpl) {

            return registrarSalidaConTransaccion(
                    idProducto, cantidad, idUsuario, motivo);
        }

        // Lógica para DAO simulados y pruebas unitarias
        try {
            Producto producto = productoDAO.buscarPorId(idProducto);

            if (producto == null) {
                return false;
            }

            int stockActual = producto.getStockActual();

            if (cantidad > stockActual) {
                return false;
            }

            int nuevoStock = stockActual - cantidad;
            producto.setStockActual(nuevoStock);

            if (!productoDAO.actualizar(producto)) {
                return false;
            }

            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipo("SALIDA");
            movimiento.setCantidad(cantidad);
            movimiento.setSaldo(nuevoStock);
            movimiento.setMotivo(motivo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(idUsuario);

            movimientoDAO.guardar(movimiento);
            return true;

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar salida: " + e.getMessage());
            return false;
        }
    }

    // Entrada con transacción
    private boolean registrarEntradaConTransaccion(
            int idProducto,
            int cantidad,
            int idUsuario,
            String motivo) {

        Connection conexion = null;

        try {
            conexion = ConexionDB.obtenerConexionTransaccion();

            ProductoDAOImpl productoDAOImpl = (ProductoDAOImpl) productoDAO;
            MovimientoInventarioDAOImpl movimientoDAOImpl =
                    (MovimientoInventarioDAOImpl) movimientoDAO;

            Producto producto =
                    productoDAOImpl.buscarPorId(idProducto, conexion);

            if (producto == null) {
                throw new RuntimeException("El producto no existe.");
            }

            int nuevoStock = producto.getStockActual() + cantidad;
            producto.setStockActual(nuevoStock);

            if (!productoDAOImpl.actualizar(producto, conexion)) {
                throw new RuntimeException("No se pudo actualizar el stock.");
            }

            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipo("ENTRADA");
            movimiento.setCantidad(cantidad);
            movimiento.setSaldo(nuevoStock);
            movimiento.setMotivo(motivo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(idUsuario);

            movimientoDAOImpl.guardar(movimiento, conexion);
            conexion.commit();

            return true;

        } catch (Exception e) {
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
                    "Transacción de entrada cancelada: " + e.getMessage());
            return false;

        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.out.println(
                            "Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    // Salida con transacción
    private boolean registrarSalidaConTransaccion(
            int idProducto,
            int cantidad,
            int idUsuario,
            String motivo) {

        Connection conexion = null;

        try {
            conexion = ConexionDB.obtenerConexionTransaccion();

            ProductoDAOImpl productoDAOImpl = (ProductoDAOImpl) productoDAO;
            MovimientoInventarioDAOImpl movimientoDAOImpl =
                    (MovimientoInventarioDAOImpl) movimientoDAO;

            Producto producto =
                    productoDAOImpl.buscarPorId(idProducto, conexion);

            if (producto == null) {
                throw new RuntimeException("El producto no existe.");
            }

            int stockActual = producto.getStockActual();

            if (cantidad > stockActual) {
                throw new RuntimeException("Stock insuficiente.");
            }

            int nuevoStock = stockActual - cantidad;
            producto.setStockActual(nuevoStock);

            if (!productoDAOImpl.actualizar(producto, conexion)) {
                throw new RuntimeException("No se pudo actualizar el stock.");
            }

            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setTipo("SALIDA");
            movimiento.setCantidad(cantidad);
            movimiento.setSaldo(nuevoStock);
            movimiento.setMotivo(motivo);
            movimiento.setIdProducto(idProducto);
            movimiento.setIdUsuario(idUsuario);

            movimientoDAOImpl.guardar(movimiento, conexion);
            conexion.commit();

            return true;

        } catch (Exception e) {
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
                    "Transacción de salida cancelada: " + e.getMessage());
            return false;

        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    System.out.println(
                            "Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }
}
