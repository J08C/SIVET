
package com.sivet.service;

import com.sivet.dao.ProductoDAO;
import com.sivet.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Lógica de negocio relacionada con productos
@Service
public class ProductoService {

    private final ProductoDAO productoDAO;

    // Spring debe utilizar este constructor
    @Autowired
    public ProductoService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    // Constructor vacío conservado para pruebas existentes
    public ProductoService() {
        this.productoDAO = null;
    }

    public boolean hayStockSuficiente(
            int stockDisponible,
            int cantidadVenta) {
        return cantidadVenta <= stockDisponible;
    }

    public boolean registrarProducto(Producto producto) {
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

    public List<Producto> listarProductos() {
        if (productoDAO == null) {
            return new ArrayList<>();
        }

        return productoDAO.listar();
    }

    public boolean actualizarProducto(Producto producto) {
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

    public boolean eliminarProducto(int idProducto) {
        if (idProducto <= 0) {
            return false;
        }

        if (productoDAO == null) {
            return false;
        }

        return productoDAO.eliminar(idProducto);
    }
}
