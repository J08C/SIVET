package com.sivet.controller;

import com.sivet.model.Usuario;
import com.sivet.service.AuthService;

/**
 * Controller encargado del inicio de sesión.
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Intenta iniciar sesión.
     *
     * @param usuario nombre de usuario
     * @param contrasena contraseña
     * @return Usuario autenticado o null
     */
    public Usuario iniciarSesion(
            String usuario,
            String contrasena) {

        return authService.iniciarSesion(
                usuario,
                contrasena
        );
    }

    /**
     * Verifica si el usuario tiene un determinado rol.
     */
    public boolean tieneRol(
            Usuario usuario,
            int idRol) {

        return authService.tieneRol(
                usuario,
                idRol
        );
    }
}