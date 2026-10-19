package com.sivet.controller;

import com.sivet.model.MovimientoInventario;
import com.sivet.service.InventarioService;

// Controlador encargado de gestionar el inventario
public class InventarioController {

    // Service que contiene la lógica de negocio
    private final InventarioService inventarioService;

    // Inyección de dependencia
    public InventarioController(
            InventarioService inventarioService) {

        this.inventarioService = inventarioService;
    }

    // Registra un movimiento de inventario
    public boolean registrarMovimiento(
            MovimientoInventario movimiento) {

        return inventarioService.registrarMovimiento(
                movimiento
        );
    }
}