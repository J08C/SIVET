package com.sivet.controller;

import com.sivet.service.ProductoService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para ProductoController
public class ProductoControllerTest {

    @Test
    void debeVerificarStockSuficiente() {

        // Creamos el Service
        ProductoService productoService =
                new ProductoService();

        // Creamos el Controller
        ProductoController controller =
                new ProductoController(productoService);

        // Verificamos un stock suficiente
        boolean resultado =
                controller.verificarStock(10, 5);

        // Debe devolver true
        assertTrue(resultado);
    }

    @Test
    void debeRechazarCuandoNoHayStock() {

        // Creamos el Service
        ProductoService productoService =
                new ProductoService();

        // Creamos el Controller
        ProductoController controller =
                new ProductoController(productoService);

        // Intentamos vender más de lo disponible
        boolean resultado =
                controller.verificarStock(3, 5);

        // Debe devolver false
        assertFalse(resultado);
    }
}