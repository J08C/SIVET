package com.sivet.dao;

import com.sivet.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de CategoriaDAO
public class CategoriaDAOImpl implements CategoriaDAO {

    // Guardar categoria
    @Override
    public void guardar(Categoria categoria) {

        String sql = "INSERT INTO CATEGORIA (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, categoria.getNombre());

            statement.executeUpdate();

            // Obtener ID generado
            try (ResultSet claves = statement.getGeneratedKeys()) {

                if (claves.next()) {
                    categoria.setIdCategoria(claves.getInt(1));
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar la categoria.",
                    e
            );
        }
    }

    // Buscar categoria por ID
    @Override
    public Categoria buscarPorId(int idCategoria) {

        String sql =
                "SELECT id_categoria, nombre " +
                "FROM CATEGORIA " +
                "WHERE id_categoria = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idCategoria);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return convertirCategoria(resultado);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar la categoria.",
                    e
            );
        }

        return null;
    }

    // Listar todas las categorias
    @Override
    public List<Categoria> listar() {

        String sql =
                "SELECT id_categoria, nombre " +
                "FROM CATEGORIA " +
                "ORDER BY id_categoria";

        List<Categoria> categorias = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                categorias.add(convertirCategoria(resultado));
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar las categorias.",
                    e
            );
        }

        return categorias;
    }

    // Actualizar categoria
    @Override
    public void actualizar(Categoria categoria) {

        String sql =
                "UPDATE CATEGORIA SET nombre = ? " +
                "WHERE id_categoria = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, categoria.getNombre());
            statement.setInt(2, categoria.getIdCategoria());

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al actualizar la categoria.",
                    e
            );
        }
    }

    // Eliminar categoria
    @Override
    public void eliminar(int idCategoria) {

        String sql =
                "DELETE FROM CATEGORIA " +
                "WHERE id_categoria = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idCategoria);

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al eliminar la categoria.",
                    e
            );
        }
    }

    // Convertir resultado SQL a objeto Categoria
    private Categoria convertirCategoria(ResultSet resultado)
            throws SQLException {

        Categoria categoria = new Categoria();

        categoria.setIdCategoria(
                resultado.getInt("id_categoria")
        );

        categoria.setNombre(
                resultado.getString("nombre")
        );

        return categoria;
    }
}