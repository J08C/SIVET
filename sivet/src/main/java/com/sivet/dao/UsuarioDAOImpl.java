package com.sivet.dao;

import com.sivet.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de UsuarioDAO
public class UsuarioDAOImpl implements UsuarioDAO {

    // Guardar usuario
    @Override
    public void guardar(Usuario usuario) {

        String sql = """
                INSERT INTO USUARIO
                (nombre_completo, usuario, contrasena_hash, id_rol)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, usuario.getNombreCompleto());
            statement.setString(2, usuario.getUsuario());
            statement.setString(3, usuario.getContrasenaHash());
            statement.setInt(4, usuario.getIdRol());

            statement.executeUpdate();

            // Obtener ID generado
            try (ResultSet claves = statement.getGeneratedKeys()) {

                if (claves.next()) {
                    usuario.setIdUsuario(claves.getInt(1));
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el usuario.",
                    e
            );
        }
    }

    // Buscar usuario por ID
    @Override
    public Usuario buscarPorId(int idUsuario) {

        String sql = """
                SELECT id_usuario, nombre_completo,
                       usuario, contrasena_hash, id_rol
                FROM USUARIO
                WHERE id_usuario = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return convertirUsuario(resultado);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar el usuario.",
                    e
            );
        }

        return null;
    }

    // Buscar usuario por nombre de usuario
    @Override
    public Usuario buscarPorUsuario(String usuario) {

        String sql = """
                SELECT id_usuario, nombre_completo,
                       usuario, contrasena_hash, id_rol
                FROM USUARIO
                WHERE usuario = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, usuario);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return convertirUsuario(resultado);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar el usuario por nombre.",
                    e
            );
        }

        return null;
    }

    // Listar usuarios
    @Override
    public List<Usuario> listar() {

        String sql = """
                SELECT id_usuario, nombre_completo,
                       usuario, contrasena_hash, id_rol
                FROM USUARIO
                ORDER BY id_usuario
                """;

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                usuarios.add(convertirUsuario(resultado));
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar los usuarios.",
                    e
            );
        }

        return usuarios;
    }

    // Actualizar usuario
    @Override
    public void actualizar(Usuario usuario) {

        String sql = """
                UPDATE USUARIO
                SET nombre_completo = ?,
                    usuario = ?,
                    contrasena_hash = ?,
                    id_rol = ?
                WHERE id_usuario = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombreCompleto());
            statement.setString(2, usuario.getUsuario());
            statement.setString(3, usuario.getContrasenaHash());
            statement.setInt(4, usuario.getIdRol());
            statement.setInt(5, usuario.getIdUsuario());

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al actualizar el usuario.",
                    e
            );
        }
    }

    // Eliminar usuario
    @Override
    public void eliminar(int idUsuario) {

        String sql =
                "DELETE FROM USUARIO WHERE id_usuario = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al eliminar el usuario.",
                    e
            );
        }
    }

    // Convertir resultado SQL a objeto Usuario
    private Usuario convertirUsuario(ResultSet resultado)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(
                resultado.getInt("id_usuario")
        );

        usuario.setNombreCompleto(
                resultado.getString("nombre_completo")
        );

        usuario.setUsuario(
                resultado.getString("usuario")
        );

        usuario.setContrasenaHash(
                resultado.getString("contrasena_hash")
        );

        usuario.setIdRol(
                resultado.getInt("id_rol")
        );

        return usuario;
    }
}