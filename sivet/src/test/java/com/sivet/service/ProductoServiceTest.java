package com.sivet.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para ProductoService
public class ProductoServiceTest {

    @Test
    void debeAceptarStockSuficiente() {

        // Creamos el Service
        ProductoService productoService =
                new ProductoService();

        // Stock disponible: 10
        // Cantidad a vender: 5
        boolean resultado =
                productoService.hayStockSuficiente(
                        10,
                        5
                );

        // Debe permitir la venta
        assertTrue(resultado);
    }

    @Test
    void debeRechazarCuandoNoHayStock() {

        // Creamos el Service
        ProductoService productoService =
                new ProductoService();

        // Stock disponible: 3
        // Cantidad a vender: 5
        boolean resultado =
                productoService.hayStockSuficiente(
                        3,
                        5
                );

        // No debe permitir la venta
        assertFalse(resultado);
    }
}