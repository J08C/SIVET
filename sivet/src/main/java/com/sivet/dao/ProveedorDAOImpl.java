
package com.sivet.dao;

import com.sivet.model.Proveedor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de ProveedorDAO
@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

    // Guardar proveedor
    @Override
    public void guardar(Proveedor proveedor) {

        String sql = """
                INSERT INTO PROVEEDOR
                (ruc, razon_social, telefono, correo, direccion, estado)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, proveedor.getRuc());
            statement.setString(2, proveedor.getRazonSocial());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getCorreo());
            statement.setString(5, proveedor.getDireccion());
            statement.setBoolean(6, proveedor.isEstado());

            statement.executeUpdate();

            // Obtener ID generado
            try (ResultSet claves = statement.getGeneratedKeys()) {
                if (claves.next()) {
                    proveedor.setIdProveedor(claves.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el proveedor.",
                    e
            );
        }
    }

    // Buscar proveedor por ID
    @Override
    public Proveedor buscarPorId(int idProveedor) {

        String sql = """
                SELECT id_proveedor, ruc, razon_social,
                       telefono, correo, direccion, estado
                FROM PROVEEDOR
                WHERE id_proveedor = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idProveedor);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return convertirProveedor(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el proveedor.",
                    e
            );
        }

        return null;
    }

    // Listar proveedores
    @Override
    public List<Proveedor> listar() {

        String sql = """
                SELECT id_proveedor, ruc, razon_social,
                       telefono, correo, direccion, estado
                FROM PROVEEDOR
                ORDER BY id_proveedor
                """;

        List<Proveedor> proveedores = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                proveedores.add(convertirProveedor(resultado));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los proveedores.",
                    e
            );
        }

        return proveedores;
    }

    // Actualizar proveedor
    @Override
    public void actualizar(Proveedor proveedor) {

        String sql = """
                UPDATE PROVEEDOR
                SET ruc = ?,
                    razon_social = ?,
                    telefono = ?,
                    correo = ?,
                    direccion = ?,
                    estado = ?
                WHERE id_proveedor = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, proveedor.getRuc());
            statement.setString(2, proveedor.getRazonSocial());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getCorreo());
            statement.setString(5, proveedor.getDireccion());
            statement.setBoolean(6, proveedor.isEstado());
            statement.setInt(7, proveedor.getIdProveedor());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el proveedor.",
                    e
            );
        }
    }

    // Eliminar proveedor
    @Override
    public void eliminar(int idProveedor) {

        String sql =
                "DELETE FROM PROVEEDOR WHERE id_proveedor = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idProveedor);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el proveedor.",
                    e
            );
        }
    }

    // Convertir resultado SQL a objeto Proveedor
    private Proveedor convertirProveedor(ResultSet resultado)
            throws SQLException {

        Proveedor proveedor = new Proveedor();

        proveedor.setIdProveedor(
                resultado.getInt("id_proveedor")
        );
        proveedor.setRuc(
                resultado.getString("ruc")
        );
        proveedor.setRazonSocial(
                resultado.getString("razon_social")
        );
        proveedor.setTelefono(
                resultado.getString("telefono")
        );
        proveedor.setCorreo(
                resultado.getString("correo")
        );
        proveedor.setDireccion(
                resultado.getString("direccion")
        );
        proveedor.setEstado(
                resultado.getBoolean("estado")
        );

        return proveedor;
    }
}
