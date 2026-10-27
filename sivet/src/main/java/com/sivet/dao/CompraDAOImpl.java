
package com.sivet.dao;

import com.sivet.model.Compra;
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
public class CompraDAOImpl implements CompraDAO {

    @Override
    public void guardar(Compra compra) {

        /*
         * PostgreSQL coloca automáticamente la fecha
         * utilizando DEFAULT CURRENT_TIMESTAMP.
         */
        String sql = """
                INSERT INTO COMPRA
                (total, id_usuario, id_proveedor)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(compra, statement);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar la compra.", e);
        }
    }

    // Guardar dentro de una transacción
    public void guardar(Compra compra, Connection conexion) {

        /*
         * PostgreSQL utiliza DEFAULT CURRENT_TIMESTAMP
         * para asignar la fecha de la compra.
         */
        String sql = """
                INSERT INTO COMPRA
                (total, id_usuario, id_proveedor)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(compra, statement);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar la compra en la transacción.",
                    e);
        }
    }

    // Ejecutar guardado
    private void ejecutarGuardar(
            Compra compra,
            PreparedStatement statement) throws SQLException {

        statement.setDouble(1, compra.getTotal());
        statement.setInt(2, compra.getIdUsuario());
        statement.setInt(3, compra.getIdProveedor());

        statement.executeUpdate();

        try (ResultSet claves = statement.getGeneratedKeys()) {
            if (claves.next()) {
                compra.setIdCompra(claves.getInt(1));
            }
        }
    }

    // Buscar por ID
    @Override
    public Compra buscarPorId(int idCompra) {

        String sql = """
                SELECT id_compra,
                       fecha,
                       total,
                       id_usuario,
                       id_proveedor
                FROM COMPRA
                WHERE id_compra = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idCompra);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return convertirCompra(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la compra.", e);
        }

        return null;
    }

    // Listar compras
    @Override
    public List<Compra> listar() {

        String sql = """
                SELECT id_compra,
                       fecha,
                       total,
                       id_usuario,
                       id_proveedor
                FROM COMPRA
                ORDER BY id_compra
                """;

        List<Compra> compras = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                compras.add(convertirCompra(resultado));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar las compras.", e);
        }

        return compras;
    }

    // Actualizar compra
    @Override
    public void actualizar(Compra compra) {

        String sql = """
                UPDATE COMPRA
                SET fecha = ?,
                    total = ?,
                    id_usuario = ?,
                    id_proveedor = ?
                WHERE id_compra = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            LocalDateTime fecha = compra.getFecha();

            if (fecha == null) {
                fecha = LocalDateTime.now();
                compra.setFecha(fecha);
            }

            statement.setTimestamp(
                    1, Timestamp.valueOf(fecha));
            statement.setDouble(
                    2, compra.getTotal());
            statement.setInt(
                    3, compra.getIdUsuario());
            statement.setInt(
                    4, compra.getIdProveedor());
            statement.setInt(
                    5, compra.getIdCompra());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar la compra.", e);
        }
    }

    // Eliminar compra
    @Override
    public void eliminar(int idCompra) {

        String sql =
                "DELETE FROM COMPRA WHERE id_compra = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idCompra);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar la compra.", e);
        }
    }

    // Convertir ResultSet a Compra
    private Compra convertirCompra(
            ResultSet resultado) throws SQLException {

        Compra compra = new Compra();

        compra.setIdCompra(
                resultado.getInt("id_compra"));

        Timestamp fecha = resultado.getTimestamp("fecha");

        if (fecha != null) {
            compra.setFecha(fecha.toLocalDateTime());
        }

        compra.setTotal(
                resultado.getDouble("total"));

        compra.setIdUsuario(
                resultado.getInt("id_usuario"));

        compra.setIdProveedor(
                resultado.getInt("id_proveedor"));

        return compra;
    }
}
