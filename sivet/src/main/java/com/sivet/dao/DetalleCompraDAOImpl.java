package com.sivet.dao;

import com.sivet.model.DetalleCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de DetalleCompraDAO
public class DetalleCompraDAOImpl implements DetalleCompraDAO {

    private static final String SQL_INSERTAR = """
            INSERT INTO DETALLE_COMPRA
            (cantidad, costo_unitario, subtotal,
             id_compra, id_producto)
            VALUES (?, ?, ?, ?, ?)
            """;

    // Guardar detalle usando una conexión propia.
    @Override
    public void guardar(DetalleCompra detalle) {

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            guardar(detalle, conexion);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el detalle de compra.", e);
        }
    }

    // Guardar detalle usando la conexión de una transacción existente.
    // No cierra la conexión ni ejecuta commit/rollback:
    // eso corresponde al servicio que controla la transacción.
    public void guardar(
            DetalleCompra detalle,
            Connection conexion) {

        if (detalle == null) {
            throw new IllegalArgumentException(
                    "El detalle de compra no puede ser nulo.");
        }

        if (conexion == null) {
            throw new IllegalArgumentException(
                    "La conexión no puede ser nula.");
        }

        try (PreparedStatement statement = conexion.prepareStatement(
                SQL_INSERTAR,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, detalle.getCantidad());
            statement.setDouble(2, detalle.getCostoUnitario());
            statement.setDouble(3, detalle.getSubtotal());
            statement.setInt(4, detalle.getIdCompra());
            statement.setInt(5, detalle.getIdProducto());

            statement.executeUpdate();

            try (ResultSet claves = statement.getGeneratedKeys()) {
                if (claves.next()) {
                    detalle.setIdDetalleCompra(claves.getInt(1));
                } else {
                    throw new SQLException(
                            "No se generó el ID del detalle de compra.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el detalle de compra.", e);
        }
    }

    // Buscar detalle por ID.
    @Override
    public DetalleCompra buscarPorId(int idDetalleCompra) {

        String sql = """
                SELECT id_detalle_compra, cantidad,
                       costo_unitario, subtotal,
                       id_compra, id_producto
                FROM DETALLE_COMPRA
                WHERE id_detalle_compra = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            try (PreparedStatement statement =
                         conexion.prepareStatement(sql)) {

                statement.setInt(1, idDetalleCompra);

                try (ResultSet resultado = statement.executeQuery()) {
                    if (resultado.next()) {
                        return convertirDetalle(resultado);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el detalle de compra.", e);
        }

        return null;
    }

    // Listar todos los detalles.
    @Override
    public List<DetalleCompra> listar() {

        String sql = """
                SELECT id_detalle_compra, cantidad,
                       costo_unitario, subtotal,
                       id_compra, id_producto
                FROM DETALLE_COMPRA
                ORDER BY id_detalle_compra
                """;

        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            try (PreparedStatement statement =
                         conexion.prepareStatement(sql);
                 ResultSet resultado = statement.executeQuery()) {

                while (resultado.next()) {
                    detalles.add(convertirDetalle(resultado));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los detalles de compra.", e);
        }

        return detalles;
    }

    // Listar detalles pertenecientes a una compra.
    @Override
    public List<DetalleCompra> listarPorCompra(int idCompra) {

        String sql = """
                SELECT id_detalle_compra, cantidad,
                       costo_unitario, subtotal,
                       id_compra, id_producto
                FROM DETALLE_COMPRA
                WHERE id_compra = ?
                ORDER BY id_detalle_compra
                """;

        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            try (PreparedStatement statement =
                         conexion.prepareStatement(sql)) {

                statement.setInt(1, idCompra);

                try (ResultSet resultado = statement.executeQuery()) {
                    while (resultado.next()) {
                        detalles.add(convertirDetalle(resultado));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar detalles de la compra.", e);
        }

        return detalles;
    }

    // Actualizar detalle.
    @Override
    public void actualizar(DetalleCompra detalle) {

        String sql = """
                UPDATE DETALLE_COMPRA
                SET cantidad = ?,
                    costo_unitario = ?,
                    subtotal = ?,
                    id_compra = ?,
                    id_producto = ?
                WHERE id_detalle_compra = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            try (PreparedStatement statement =
                         conexion.prepareStatement(sql)) {

                statement.setInt(1, detalle.getCantidad());
                statement.setDouble(2, detalle.getCostoUnitario());
                statement.setDouble(3, detalle.getSubtotal());
                statement.setInt(4, detalle.getIdCompra());
                statement.setInt(5, detalle.getIdProducto());
                statement.setInt(6, detalle.getIdDetalleCompra());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el detalle de compra.", e);
        }
    }

    // Eliminar detalle.
    @Override
    public void eliminar(int idDetalleCompra) {

        String sql = """
                DELETE FROM DETALLE_COMPRA
                WHERE id_detalle_compra = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion == null) {
                throw new SQLException(
                        "No se pudo obtener conexión con PostgreSQL.");
            }

            try (PreparedStatement statement =
                         conexion.prepareStatement(sql)) {

                statement.setInt(1, idDetalleCompra);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el detalle de compra.", e);
        }
    }

    // Convertir resultado SQL a objeto DetalleCompra.
    private DetalleCompra convertirDetalle(ResultSet resultado)
            throws SQLException {

        DetalleCompra detalle = new DetalleCompra();

        detalle.setIdDetalleCompra(
                resultado.getInt("id_detalle_compra"));
        detalle.setCantidad(
                resultado.getInt("cantidad"));
        detalle.setCostoUnitario(
                resultado.getDouble("costo_unitario"));
        detalle.setSubtotal(
                resultado.getDouble("subtotal"));
        detalle.setIdCompra(
                resultado.getInt("id_compra"));
        detalle.setIdProducto(
                resultado.getInt("id_producto"));

        return detalle;
    }
}