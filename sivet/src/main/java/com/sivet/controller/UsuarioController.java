package com.sivet.controller;

import com.sivet.model.Usuario;
import com.sivet.service.UsuarioService;

// Controlador encargado de gestionar las operaciones de usuarios
public class UsuarioController {

    // Service que contiene la lógica de negocio
    private final UsuarioService usuarioService;

    // Inyección de dependencia
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registra un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {

        return usuarioService.registrarUsuario(usuario);
    }
}