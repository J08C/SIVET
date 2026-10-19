package com.sivet.dao;

import com.sivet.model.Producto;

import java.util.List;

// DAO encargado de las operaciones de productos
public interface ProductoDAO {

    // Guarda un producto
    void guardar(Producto producto);

    // Busca un producto por ID
    Producto buscarPorId(int idProducto);

    // Lista todos los productos
    List<Producto> listar();

    // Actualiza un producto
    boolean actualizar(Producto producto);

    // Elimina un producto
    boolean eliminar(int idProducto);
}