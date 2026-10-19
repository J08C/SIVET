package com.sivet.dao;

import com.sivet.model.DetalleVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de DetalleVentaDAO
public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    // Guardar detalle de venta
    @Override
    public void guardar(DetalleVenta detalle) {

        String sql = """
                INSERT INTO DETALLE_VENTA
                (cantidad, precio_unitario, subtotal,
                 id_venta, id_producto)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(
                    detalle,
                    statement
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el detalle de venta.",
                    e
            );
        }
    }

    // Guardar detalle utilizando una conexión existente.
    // Se utiliza dentro de una transacción.
    public void guardar(
            DetalleVenta detalle,
            Connection conexion) {

        String sql = """
                INSERT INTO DETALLE_VENTA
                (cantidad, precio_unitario, subtotal,
                 id_venta, id_producto)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(
                    detalle,
                    statement
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el detalle "
                    + "de venta en la transacción.",
                    e
            );
        }
    }

    // Método privado común para guardar
    private void ejecutarGuardar(
            DetalleVenta detalle,
            PreparedStatement statement)
            throws SQLException {

        statement.setInt(
                1,
                detalle.getCantidad()
        );

        statement.setDouble(
                2,
                detalle.getPrecioUnitario()
        );

        statement.setDouble(
                3,
                detalle.getSubtotal()
        );

        statement.setInt(
                4,
                detalle.getIdVenta()
        );

        statement.setInt(
                5,
                detalle.getIdProducto()
        );

        statement.executeUpdate();

        // Obtener ID generado por PostgreSQL
        try (ResultSet claves =
                     statement.getGeneratedKeys()) {

            if (claves.next()) {

                detalle.setIdDetalleVenta(
                        claves.getInt(1)
                );
            }
        }
    }

    // Buscar detalle por ID
    @Override
    public DetalleVenta buscarPorId(
            int idDetalleVenta) {

        String sql = """
                SELECT id_detalle_venta,
                       cantidad,
                       precio_unitario,
                       subtotal,
                       id_venta,
                       id_producto
                FROM DETALLE_VENTA
                WHERE id_detalle_venta = ?
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idDetalleVenta
            );

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirDetalle(
                            resultado
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar el detalle "
                    + "de venta.",
                    e
            );
        }

        return null;
    }

    // Listar todos los detalles
    @Override
    public List<DetalleVenta> listar() {

        String sql = """
                SELECT id_detalle_venta,
                       cantidad,
                       precio_unitario,
                       subtotal,
                       id_venta,
                       id_producto
                FROM DETALLE_VENTA
                ORDER BY id_detalle_venta
                """;

        List<DetalleVenta> detalles =
                new ArrayList<>();

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                detalles.add(
                        convertirDetalle(
                                resultado
                        )
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar los detalles "
                    + "de venta.",
                    e
            );
        }

        return detalles;
    }

    // Listar detalles de una venta específica
    @Override
    public List<DetalleVenta> listarPorVenta(
            int idVenta) {

        String sql = """
                SELECT id_detalle_venta,
                       cantidad,
                       precio_unitario,
                       subtotal,
                       id_venta,
                       id_producto
                FROM DETALLE_VENTA
                WHERE id_venta = ?
                ORDER BY id_detalle_venta
                """;

        List<DetalleVenta> detalles =
                new ArrayList<>();

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idVenta
            );

            try (ResultSet resultado =
                         statement.executeQuery()) {

                while (resultado.next()) {

                    detalles.add(
                            convertirDetalle(
                                    resultado
                            )
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar detalles "
                    + "de la venta.",
                    e
            );
        }

        return detalles;
    }

    // Actualizar detalle
    @Override
    public void actualizar(
            DetalleVenta detalle) {

        String sql = """
                UPDATE DETALLE_VENTA
                SET cantidad = ?,
                    precio_unitario = ?,
                    subtotal = ?,
                    id_venta = ?,
                    id_producto = ?
                WHERE id_detalle_venta = ?
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    detalle.getCantidad()
            );

            statement.setDouble(
                    2,
                    detalle.getPrecioUnitario()
            );

            statement.setDouble(
                    3,
                    detalle.getSubtotal()
            );

            statement.setInt(
                    4,
                    detalle.getIdVenta()
            );

            statement.setInt(
                    5,
                    detalle.getIdProducto()
            );

            statement.setInt(
                    6,
                    detalle.getIdDetalleVenta()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al actualizar el detalle "
                    + "de venta.",
                    e
            );
        }
    }

    // Eliminar detalle
    @Override
    public void eliminar(
            int idDetalleVenta) {

        String sql =
                "DELETE FROM DETALLE_VENTA "
                + "WHERE id_detalle_venta = ?";

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idDetalleVenta
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al eliminar el detalle "
                    + "de venta.",
                    e
            );
        }
    }

    // Convertir resultado SQL a objeto DetalleVenta
    private DetalleVenta convertirDetalle(
            ResultSet resultado)
            throws SQLException {

        DetalleVenta detalle =
                new DetalleVenta();

        detalle.setIdDetalleVenta(
                resultado.getInt(
                        "id_detalle_venta"
                )
        );

        detalle.setCantidad(
                resultado.getInt(
                        "cantidad"
                )
        );

        detalle.setPrecioUnitario(
                resultado.getDouble(
                        "precio_unitario"
                )
        );

        detalle.setSubtotal(
                resultado.getDouble(
                        "subtotal"
                )
        );

        detalle.setIdVenta(
                resultado.getInt(
                        "id_venta"
                )
        );

        detalle.setIdProducto(
                resultado.getInt(
                        "id_producto"
                )
        );

        return detalle;
    }
}