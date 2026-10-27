
package com.sivet.service;

import com.sivet.dao.UsuarioDAO;
import com.sivet.model.Usuario;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de autenticar usuarios.
 */
@Service
public class AuthService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordService passwordService;

    public AuthService(
            UsuarioDAO usuarioDAO,
            PasswordService passwordService) {

        this.usuarioDAO = usuarioDAO;
        this.passwordService = passwordService;
    }

    /**
     * Autentica un usuario mediante usuario y contraseña.
     *
     * @param nombreUsuario nombre de usuario
     * @param contrasena contraseña ingresada
     * @return Usuario autenticado o null si las credenciales son incorrectas
     */
    public Usuario iniciarSesion(
            String nombreUsuario,
            String contrasena) {

        if (nombreUsuario == null
                || nombreUsuario.isBlank()) {
            return null;
        }

        if (contrasena == null
                || contrasena.isBlank()) {
            return null;
        }

        Usuario usuario =
                usuarioDAO.buscarPorUsuario(
                        nombreUsuario.trim()
                );

        if (usuario == null) {
            return null;
        }

        boolean contrasenaCorrecta =
                passwordService.verificar(
                        contrasena,
                        usuario.getContrasenaHash()
                );

        if (!contrasenaCorrecta) {
            return null;
        }

        return usuario;
    }

    /**
     * Verifica si un usuario tiene un determinado rol.
     *
     * @param usuario usuario autenticado
     * @param idRol ID del rol
     * @return true si el usuario tiene ese rol
     */
    public boolean tieneRol(
            Usuario usuario,
            int idRol) {

        if (usuario == null || idRol <= 0) {
            return false;
        }

        return usuario.getIdRol() == idRol;
    }
}
