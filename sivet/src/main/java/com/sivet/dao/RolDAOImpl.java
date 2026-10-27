
package com.sivet.dao;

import com.sivet.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

// Implementación de las operaciones de acceso a datos de Rol
@Repository
public class RolDAOImpl implements RolDAO {

    // Registrar un nuevo rol
    @Override
    public void guardar(Rol rol) {

        String sql = "INSERT INTO ROL (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, rol.getNombre());
            statement.executeUpdate();

            try (ResultSet clavesGeneradas = statement.getGeneratedKeys()) {
                if (clavesGeneradas.next()) {
                    rol.setIdRol(clavesGeneradas.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el rol.",
                    e
            );
        }
    }

    // Buscar un rol por su ID
    @Override
    public Rol buscarPorId(int idRol) {

        String sql =
                "SELECT id_rol, nombre FROM ROL WHERE id_rol = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idRol);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return convertirRol(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el rol.",
                    e
            );
        }

        return null;
    }

    // Listar todos los roles
    @Override
    public List<Rol> listar() {

        String sql =
                "SELECT id_rol, nombre FROM ROL ORDER BY id_rol";

        List<Rol> roles = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                roles.add(convertirRol(resultado));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los roles.",
                    e
            );
        }

        return roles;
    }

    // Actualizar un rol
    @Override
    public void actualizar(Rol rol) {

        String sql =
                "UPDATE ROL SET nombre = ? WHERE id_rol = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, rol.getNombre());
            statement.setInt(2, rol.getIdRol());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el rol.",
                    e
            );
        }
    }

    // Eliminar un rol
    @Override
    public void eliminar(int idRol) {

        String sql =
                "DELETE FROM ROL WHERE id_rol = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idRol);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el rol.",
                    e
            );
        }
    }

    // Convierte un registro de PostgreSQL en un objeto Rol
    private Rol convertirRol(ResultSet resultado)
            throws SQLException {

        Rol rol = new Rol();

        rol.setIdRol(resultado.getInt("id_rol"));
        rol.setNombre(resultado.getString("nombre"));

        return rol;
    }
}
