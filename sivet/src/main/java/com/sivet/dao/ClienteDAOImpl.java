package com.sivet.dao;

import com.sivet.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementación de ClienteDAO
public class ClienteDAOImpl implements ClienteDAO {

    // Guardar cliente
    @Override
    public void guardar(Cliente cliente) {

        String sql = """
                INSERT INTO CLIENTE
                (dni_ruc, nombres, telefono, direccion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, cliente.getDniRuc());
            statement.setString(2, cliente.getNombres());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getDireccion());

            statement.executeUpdate();

            // Obtener ID generado por PostgreSQL
            try (ResultSet claves = statement.getGeneratedKeys()) {

                if (claves.next()) {
                    cliente.setIdCliente(claves.getInt(1));
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al guardar el cliente.",
                    e
            );
        }
    }

    // Buscar cliente por ID
    @Override
    public Cliente buscarPorId(int idCliente) {

        String sql = """
                SELECT id_cliente, dni_ruc, nombres,
                       telefono, direccion
                FROM CLIENTE
                WHERE id_cliente = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idCliente);

            try (ResultSet resultado = statement.executeQuery()) {

                if (resultado.next()) {
                    return convertirCliente(resultado);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al buscar el cliente.",
                    e
            );
        }

        return null;
    }

    // Listar clientes
    @Override
    public List<Cliente> listar() {

        String sql = """
                SELECT id_cliente, dni_ruc, nombres,
                       telefono, direccion
                FROM CLIENTE
                ORDER BY id_cliente
                """;

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                clientes.add(convertirCliente(resultado));
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al listar los clientes.",
                    e
            );
        }

        return clientes;
    }

    // Actualizar cliente
    @Override
    public void actualizar(Cliente cliente) {

        String sql = """
                UPDATE CLIENTE
                SET dni_ruc = ?,
                    nombres = ?,
                    telefono = ?,
                    direccion = ?
                WHERE id_cliente = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, cliente.getDniRuc());
            statement.setString(2, cliente.getNombres());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getDireccion());
            statement.setInt(5, cliente.getIdCliente());

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al actualizar el cliente.",
                    e
            );
        }
    }

    // Eliminar cliente
    @Override
    public void eliminar(int idCliente) {

        String sql =
                "DELETE FROM CLIENTE WHERE id_cliente = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idCliente);

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error al eliminar el cliente.",
                    e
            );
        }
    }

    // Convertir resultado SQL a objeto Cliente
    private Cliente convertirCliente(ResultSet resultado)
            throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setIdCliente(
                resultado.getInt("id_cliente")
        );

        cliente.setDniRuc(
                resultado.getString("dni_ruc")
        );

        cliente.setNombres(
                resultado.getString("nombres")
        );

        cliente.setTelefono(
                resultado.getString("telefono")
        );

        cliente.setDireccion(
                resultado.getString("direccion")
        );

        return cliente;
    }
}