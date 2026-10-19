package com.sivet.service;

import com.sivet.dao.UsuarioDAO;
import com.sivet.model.Usuario;

// Contiene la lógica de negocio relacionada con los usuarios
public class UsuarioService {

    // Dependemos de la interfaz UsuarioDAO
    private final UsuarioDAO usuarioDAO;

    // Inyección de dependencia
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Registra un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {

        // El usuario no puede ser nulo
        if (usuario == null) {
            return false;
        }

        // El nombre completo es obligatorio
        if (usuario.getNombreCompleto() == null
                || usuario.getNombreCompleto().trim().isEmpty()) {
            return false;
        }

        // El nombre de usuario es obligatorio
        if (usuario.getUsuario() == null
                || usuario.getUsuario().trim().isEmpty()) {
            return false;
        }

        // La contraseña cifrada es obligatoria
        if (usuario.getContrasenaHash() == null
                || usuario.getContrasenaHash().trim().isEmpty()) {
            return false;
        }

        // El rol debe ser válido
        if (usuario.getIdRol() <= 0) {
            return false;
        }

        // Guardamos el usuario
        usuarioDAO.guardar(usuario);

        return true;
    }
}