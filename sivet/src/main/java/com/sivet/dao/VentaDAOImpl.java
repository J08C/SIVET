
package com.sivet.dao;

import com.sivet.model.Venta;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VentaDAOImpl implements VentaDAO {

    @Override
    public void guardar(Venta venta) {

        String sql = """
                INSERT INTO VENTA
                (fecha, total, id_usuario, id_cliente)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(venta, statement);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar la venta.", e);
        }
    }

    // Versión para utilizar dentro de una transacción
    public void guardar(Venta venta, Connection conexion) {

        String sql = """
                INSERT INTO VENTA
                (fecha, total, id_usuario, id_cliente)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(venta, statement);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar la venta en la transacción.", e);
        }
    }

    private void ejecutarGuardar(
            Venta venta,
            PreparedStatement statement) throws SQLException {

        // Si la venta no tiene fecha, usamos la fecha y hora actual.
        LocalDateTime fecha = venta.getFecha();

        if (fecha == null) {
            fecha = LocalDateTime.now();
            venta.setFecha(fecha);
        }

        statement.setTimestamp(1, Timestamp.valueOf(fecha));
        statement.setDouble(2, venta.getTotal());
        statement.setInt(3, venta.getIdUsuario());

        if (venta.getIdCliente() == null) {
            statement.setNull(4, java.sql.Types.INTEGER);
        } else {
            statement.setInt(4, venta.getIdCliente());
        }

        statement.executeUpdate();

        try (ResultSet claves = statement.getGeneratedKeys()) {
            if (claves.next()) {
                venta.setIdVenta(claves.getInt(1));
            }
        }
    }

    @Override
    public Venta buscarPorId(int idVenta) {

        String sql = """
                SELECT id_venta, fecha, total, id_usuario, id_cliente
                FROM VENTA
                WHERE id_venta = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idVenta);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return convertirVenta(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la venta.", e);
        }

        return null;
    }

    @Override
    public List<Venta> listar() {

        String sql = """
                SELECT id_venta, fecha, total, id_usuario, id_cliente
                FROM VENTA
                ORDER BY id_venta
                """;

        List<Venta> ventas = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                ventas.add(convertirVenta(resultado));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar las ventas.", e);
        }

        return ventas;
    }

    @Override
    public void actualizar(Venta venta) {

        String sql = """
                UPDATE VENTA
                SET fecha = ?,
                    total = ?,
                    id_usuario = ?,
                    id_cliente = ?
                WHERE id_venta = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            LocalDateTime fecha = venta.getFecha();

            if (fecha == null) {
                fecha = LocalDateTime.now();
                venta.setFecha(fecha);
            }

            statement.setTimestamp(1, Timestamp.valueOf(fecha));
            statement.setDouble(2, venta.getTotal());
            statement.setInt(3, venta.getIdUsuario());

            if (venta.getIdCliente() == null) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setInt(4, venta.getIdCliente());
            }

            statement.setInt(5, venta.getIdVenta());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar la venta.", e);
        }
    }

    @Override
    public void eliminar(int idVenta) {

        String sql = "DELETE FROM VENTA WHERE id_venta = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idVenta);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar la venta.", e);
        }
    }

    private Venta convertirVenta(ResultSet resultado)
            throws SQLException {

        Venta venta = new Venta();

        venta.setIdVenta(resultado.getInt("id_venta"));

        Timestamp fecha = resultado.getTimestamp("fecha");

        if (fecha != null) {
            venta.setFecha(fecha.toLocalDateTime());
        }

        venta.setTotal(resultado.getDouble("total"));
        venta.setIdUsuario(resultado.getInt("id_usuario"));

        int idCliente = resultado.getInt("id_cliente");

        if (resultado.wasNull()) {
            venta.setIdCliente(null);
        } else {
            venta.setIdCliente(idCliente);
        }

        return venta;
    }
}
