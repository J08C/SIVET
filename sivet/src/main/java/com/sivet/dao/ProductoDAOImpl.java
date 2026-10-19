package com.sivet.dao;

import com.sivet.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void guardar(Producto producto) {

        String sql = """
                INSERT INTO PRODUCTO
                (codigo, nombre, precio_compra, precio_venta,
                 stock_actual, stock_minimo, estado, id_categoria)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ejecutarGuardar(producto, statement);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar el producto.", e);
        }
    }

    private void ejecutarGuardar(
            Producto producto,
            PreparedStatement statement)
            throws SQLException {

        statement.setString(1, producto.getCodigo());
        statement.setString(2, producto.getNombre());
        statement.setDouble(3, producto.getPrecioCompra());
        statement.setDouble(4, producto.getPrecioVenta());
        statement.setInt(5, producto.getStockActual());
        statement.setInt(6, producto.getStockMinimo());
        statement.setBoolean(7, producto.isEstado());
        statement.setInt(8, producto.getIdCategoria());

        statement.executeUpdate();

        try (ResultSet claves =
                     statement.getGeneratedKeys()) {

            if (claves.next()) {
                producto.setIdProducto(
                        claves.getInt(1));
            }
        }
    }

    @Override
    public Producto buscarPorId(int idProducto) {

        try (Connection conexion =
                     ConexionDB.obtenerConexion()) {

            return buscarPorId(
                    idProducto,
                    conexion);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el producto.", e);
        }
    }

    // Versión para utilizar dentro de una transacción
    public Producto buscarPorId(
            int idProducto,
            Connection conexion) {

        String sql = """
                SELECT id_producto, codigo, nombre,
                       precio_compra, precio_venta,
                       stock_actual, stock_minimo,
                       estado, id_categoria
                FROM PRODUCTO
                WHERE id_producto = ?
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idProducto);

            try (ResultSet resultado =
                         statement.executeQuery()) {

                if (resultado.next()) {
                    return convertirProducto(resultado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el producto "
                    + "en la transacción.", e);
        }

        return null;
    }

    @Override
    public List<Producto> listar() {

        String sql = """
                SELECT id_producto, codigo, nombre,
                       precio_compra, precio_venta,
                       stock_actual, stock_minimo,
                       estado, id_categoria
                FROM PRODUCTO
                ORDER BY id_producto
                """;

        List<Producto> productos = new ArrayList<>();

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     statement.executeQuery()) {

            while (resultado.next()) {
                productos.add(
                        convertirProducto(resultado));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los productos.", e);
        }

        return productos;
    }

    @Override
    public boolean actualizar(Producto producto) {

        try (Connection conexion =
                     ConexionDB.obtenerConexion()) {

            return actualizar(
                    producto,
                    conexion);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el producto.", e);
        }
    }

    // Versión para utilizar dentro de una transacción
    public boolean actualizar(
            Producto producto,
            Connection conexion) {

        String sql = """
                UPDATE PRODUCTO
                SET codigo = ?,
                    nombre = ?,
                    precio_compra = ?,
                    precio_venta = ?,
                    stock_actual = ?,
                    stock_minimo = ?,
                    estado = ?,
                    id_categoria = ?
                WHERE id_producto = ?
                """;

        try (PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            prepararActualizacion(
                    producto,
                    statement);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el producto "
                    + "en la transacción.", e);
        }
    }

    private void prepararActualizacion(
            Producto producto,
            PreparedStatement statement)
            throws SQLException {

        statement.setString(
                1,
                producto.getCodigo());

        statement.setString(
                2,
                producto.getNombre());

        statement.setDouble(
                3,
                producto.getPrecioCompra());

        statement.setDouble(
                4,
                producto.getPrecioVenta());

        statement.setInt(
                5,
                producto.getStockActual());

        statement.setInt(
                6,
                producto.getStockMinimo());

        statement.setBoolean(
                7,
                producto.isEstado());

        statement.setInt(
                8,
                producto.getIdCategoria());

        statement.setInt(
                9,
                producto.getIdProducto());
    }

    @Override
    public boolean eliminar(int idProducto) {

        String sql =
                "DELETE FROM PRODUCTO "
                + "WHERE id_producto = ?";

        try (Connection conexion =
                     ConexionDB.obtenerConexion();
             PreparedStatement statement =
                     conexion.prepareStatement(sql)) {

            statement.setInt(1, idProducto);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el producto.", e);
        }
    }

    private Producto convertirProducto(
            ResultSet resultado)
            throws SQLException {

        Producto producto = new Producto();

        producto.setIdProducto(
                resultado.getInt("id_producto"));

        producto.setCodigo(
                resultado.getString("codigo"));

        producto.setNombre(
                resultado.getString("nombre"));

        producto.setPrecioCompra(
                resultado.getDouble("precio_compra"));

        producto.setPrecioVenta(
                resultado.getDouble("precio_venta"));

        producto.setStockActual(
                resultado.getInt("stock_actual"));

        producto.setStockMinimo(
                resultado.getInt("stock_minimo"));

        producto.setEstado(
                resultado.getBoolean("estado"));

        producto.setIdCategoria(
                resultado.getInt("id_categoria"));

        return producto;
    }
}