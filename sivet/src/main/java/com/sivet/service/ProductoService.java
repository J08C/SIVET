package com.sivet.service;

import com.sivet.dao.ProductoDAO;
import com.sivet.model.Producto;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio relacionada con productos
public class ProductoService {

    private final ProductoDAO productoDAO;

    // Constructor con DAO
    public ProductoService(
            ProductoDAO productoDAO) {

        this.productoDAO = productoDAO;
    }

    // Constructor vacío
    // Se mantiene para las pruebas de stock
    public ProductoService() {

        this.productoDAO = null;
    }

    // Verifica si existe stock suficiente
    public boolean hayStockSuficiente(
            int stockDisponible,
            int cantidadVenta) {

        return cantidadVenta <= stockDisponible;
    }

    // Registra un producto
    public boolean registrarProducto(
            Producto producto) {

        if (producto == null) {
            return false;
        }

        if (producto.getCodigo() == null
                || producto.getCodigo().trim().isEmpty()) {

            return false;
        }

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            return false;
        }

        if (producto.getPrecioCompra() < 0
                || producto.getPrecioVenta() < 0) {

            return false;
        }

        if (producto.getStockActual() < 0
                || producto.getStockMinimo() < 0) {

            return false;
        }

        if (productoDAO == null) {
            return false;
        }

        productoDAO.guardar(producto);

        return true;
    }

    // Obtiene todos los productos
    public List<Producto> listarProductos() {

        if (productoDAO == null) {

            return new ArrayList<>();
        }

        return productoDAO.listar();
    }

    // Actualiza un producto
    public boolean actualizarProducto(
            Producto producto) {

        if (producto == null) {
            return false;
        }

        if (producto.getIdProducto() <= 0) {
            return false;
        }

        if (producto.getCodigo() == null
                || producto.getCodigo().trim().isEmpty()) {

            return false;
        }

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            return false;
        }

        if (producto.getPrecioCompra() < 0
                || producto.getPrecioVenta() < 0) {

            return false;
        }

        if (producto.getStockActual() < 0
                || producto.getStockMinimo() < 0) {

            return false;
        }

        if (productoDAO == null) {
            return false;
        }

        return productoDAO.actualizar(producto);
    }

    // Elimina un producto
    public boolean eliminarProducto(
            int idProducto) {

        if (idProducto <= 0) {
            return false;
        }

        if (productoDAO == null) {
            return false;
        }

        return productoDAO.eliminar(idProducto);
    }
}