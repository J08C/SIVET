package com.sivet.controller;

import com.sivet.model.Rol;
import com.sivet.service.RolService;

// Controlador encargado de gestionar las operaciones de roles
public class RolController {

    // Service que contiene la lógica de negocio
    private final RolService rolService;

    // Inyección de dependencia
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // Registra un nuevo rol
    public boolean registrarRol(Rol rol) {

        return rolService.registrarRol(rol);
    }
}