package com.sivet.controller;

import com.sivet.model.Producto;
import com.sivet.service.ProductoService;

import java.util.List;

// Controlador encargado de gestionar productos
public class ProductoController {

    private final ProductoService productoService;

    // Constructor
    public ProductoController(
            ProductoService productoService) {

        this.productoService = productoService;
    }

    // Verifica stock suficiente
    public boolean verificarStock(
            int stockDisponible,
            int cantidadVenta) {

        return productoService.hayStockSuficiente(
                stockDisponible,
                cantidadVenta
        );
    }

    // Registra un producto
    public boolean registrarProducto(
            Producto producto) {

        return productoService.registrarProducto(
                producto
        );
    }

    // Lista productos
    public List<Producto> listarProductos() {

        return productoService.listarProductos();
    }

    // Actualiza un producto
    public boolean actualizarProducto(
            Producto producto) {

        return productoService.actualizarProducto(
                producto
        );
    }

    // Elimina un producto
    public boolean eliminarProducto(
            int idProducto) {

        return productoService.eliminarProducto(
                idProducto
        );
    }
}