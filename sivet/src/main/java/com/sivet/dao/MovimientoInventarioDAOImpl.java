package com.sivet.dao;

import com.sivet.model.MovimientoInventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoInventarioDAOImpl
        implements MovimientoInventarioDAO {

    @Override
    public void guardar(
            MovimientoInventario movimiento) {

        String sql = """
                INSERT INTO MOVIMIENTO_INVENTARIO
                (tipo, cantidad, saldo, motivo,
                 id_producto, id_usuario)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(
                    movimiento,
                    statement
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el movimiento de inventario.",
                    e
            );
        }
    }

    public void guardar(
            MovimientoInventario movimiento,
            Connection conexion) {

        String sql = """
                INSERT INTO MOVIMIENTO_INVENTARIO
                (tipo, cantidad, saldo, motivo,
                 id_producto, id_usuario)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(
                    movimiento,
                    statement
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el movimiento "
                    + "en la transacción.",
                    e
            );
        }
    }

    private void ejecutarGuardar(
            MovimientoInventario movimiento,
            PreparedStatement statement)
            throws SQLException {

        statement.setString(
                1,
                movimiento.getTipo()
        );

        statement.setInt(
                2,
                movimiento.getCantidad()
        );

        statement.setInt(
                3,
                movimiento.getSaldo()
        );

        statement.setString(
                4,
                movimiento.getMotivo()
        );

        statement.setInt(
                5,
                movimiento.getIdProducto()
        );

        statement.setInt(
                6,
                movimiento.getIdUsuario()
        );

        statement.executeUpdate();

        try (ResultSet claves =
                     statement.getGeneratedKeys()) {

            if (claves.next()) {

                movimiento.setIdMovimiento(
                        claves.getInt(1)
                );
            }
        }
    }

    @Override
    public MovimientoInventario buscarPorId(
            int idMovimiento) {

        String sql = """
                SELECT id_movimiento,
                       fecha,
                       tipo,
                       cantidad,
                       saldo,
                       motivo,
                       id_producto,
                       id_usuario
                FROM MOVIMIENTO_INVENTARIO
                WHERE id_movimiento = ?
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idMovimiento
            );

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {

                    return convertirMovimiento(
                            resultado
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar el movimiento de inventario.",
                    e
            );
        }

        return null;
    }

    @Override
    public List<MovimientoInventario> listar() {

        String sql = """
                SELECT id_movimiento,
                       fecha,
                       tipo,
                       cantidad,
                       saldo,
                       motivo,
                       id_producto,
                       id_usuario
                FROM MOVIMIENTO_INVENTARIO
                ORDER BY id_movimiento
                """;

        List<MovimientoInventario> movimientos =
                new ArrayList<>();

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {

                movimientos.add(
                        convertirMovimiento(
                                resultado
                        )
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar los movimientos de inventario.",
                    e
            );
        }

        return movimientos;
    }

    @Override
    public List<MovimientoInventario> listarPorProducto(
            int idProducto) {

        String sql = """
                SELECT id_movimiento,
                       fecha,
                       tipo,
                       cantidad,
                       saldo,
                       motivo,
                       id_producto,
                       id_usuario
                FROM MOVIMIENTO_INVENTARIO
                WHERE id_producto = ?
                ORDER BY id_movimiento
                """;

        List<MovimientoInventario> movimientos =
                new ArrayList<>();

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idProducto
            );

            try (ResultSet resultado =
                         statement.executeQuery()) {

                while (resultado.next()) {

                    movimientos.add(
                            convertirMovimiento(
                                    resultado
                            )
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar movimientos por producto.",
                    e
            );
        }

        return movimientos;
    }

    @Override
    public void actualizar(
            MovimientoInventario movimiento) {

        String sql = """
                UPDATE MOVIMIENTO_INVENTARIO
                SET fecha = ?,
                    tipo = ?,
                    cantidad = ?,
                    saldo = ?,
                    motivo = ?,
                    id_producto = ?,
                    id_usuario = ?
                WHERE id_movimiento = ?
                """;

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            LocalDateTime fecha =
                    movimiento.getFecha();

            if (fecha == null) {
                fecha = LocalDateTime.now();
                movimiento.setFecha(fecha);
            }

            statement.setTimestamp(
                    1,
                    Timestamp.valueOf(fecha)
            );

            statement.setString(
                    2,
                    movimiento.getTipo()
            );

            statement.setInt(
                    3,
                    movimiento.getCantidad()
            );

            statement.setInt(
                    4,
                    movimiento.getSaldo()
            );

            statement.setString(
                    5,
                    movimiento.getMotivo()
            );

            statement.setInt(
                    6,
                    movimiento.getIdProducto()
            );

            statement.setInt(
                    7,
                    movimiento.getIdUsuario()
            );

            statement.setInt(
                    8,
                    movimiento.getIdMovimiento()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al actualizar el movimiento de inventario.",
                    e
            );
        }
    }

    @Override
    public void eliminar(
            int idMovimiento) {

        String sql =
                "DELETE FROM MOVIMIENTO_INVENTARIO "
                + "WHERE id_movimiento = ?";

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    idMovimiento
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al eliminar el movimiento de inventario.",
                    e
            );
        }
    }

    private MovimientoInventario convertirMovimiento(
            ResultSet resultado)
            throws SQLException {

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setIdMovimiento(
                resultado.getInt(
                        "id_movimiento"
                )
        );

        Timestamp fecha =
                resultado.getTimestamp(
                        "fecha"
                );

        if (fecha != null) {

            movimiento.setFecha(
                    fecha.toLocalDateTime()
            );
        }

        movimiento.setTipo(
                resultado.getString("tipo")
        );

        movimiento.setCantidad(
                resultado.getInt("cantidad")
        );

        movimiento.setSaldo(
                resultado.getInt("saldo")
        );

        movimiento.setMotivo(
                resultado.getString("motivo")
        );

        movimiento.setIdProducto(
                resultado.getInt("id_producto")
        );

        movimiento.setIdUsuario(
                resultado.getInt("id_usuario")
        );

        return movimiento;
    }
}