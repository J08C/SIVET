package com.sivet.controller;

import com.sivet.model.Proveedor;
import com.sivet.service.ProveedorService;

import java.util.List;

// Controlador encargado de gestionar las operaciones de proveedores
public class ProveedorController {

    // Service que contiene la lógica de negocio
    private final ProveedorService proveedorService;

    // Inyección de dependencia
    public ProveedorController(
            ProveedorService proveedorService) {

        this.proveedorService = proveedorService;
    }

    // Registra un nuevo proveedor
    public boolean registrarProveedor(
            Proveedor proveedor) {

        return proveedorService.registrarProveedor(
                proveedor
        );
    }

    // Lista todos los proveedores
    public List<Proveedor> listarProveedores() {

        return proveedorService.listarProveedores();
    }

    // Busca un proveedor por ID
    public Proveedor buscarProveedorPorId(
            int idProveedor) {

        return proveedorService.buscarProveedorPorId(
                idProveedor
        );
    }

    // Actualiza un proveedor
    public boolean actualizarProveedor(
            Proveedor proveedor) {

        return proveedorService.actualizarProveedor(
                proveedor
        );
    }

    // Elimina un proveedor
    public boolean eliminarProveedor(
            int idProveedor) {

        return proveedorService.eliminarProveedor(
                idProveedor
        );
    }
}