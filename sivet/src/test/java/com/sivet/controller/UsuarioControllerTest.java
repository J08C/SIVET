package com.sivet.controller;

import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.model.Usuario;
import com.sivet.service.UsuarioService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para UsuarioController
public class UsuarioControllerTest {

    @Test
    void debeRegistrarUsuarioCorrectamente() {

        // Creamos el DAO
        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        // Creamos el Service
        UsuarioService usuarioService =
                new UsuarioService(usuarioDAO);

        // Creamos el Controller
        UsuarioController controller =
                new UsuarioController(usuarioService);

        // Creamos un usuario de prueba
        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Controller Test"
        );

        // La columna usuario permite máximo 30 caracteres.
        // Usamos un usuario corto y único.
        usuario.setUsuario(
                "UCT" +
                (System.currentTimeMillis() % 1000000)
        );

        // Hash de prueba.
        // BCrypt se implementará posteriormente
        // para la autenticación.
        usuario.setContrasenaHash(
                "hash_de_prueba"
        );

        // Utilizamos el rol existente con ID 1
        usuario.setIdRol(1);

        // Registramos el usuario
        boolean resultado =
                controller.registrarUsuario(usuario);

        // Debe registrarse correctamente
        assertTrue(resultado);

        // PostgreSQL debe generar un ID
        assertTrue(
                usuario.getIdUsuario() > 0
        );

        // Eliminamos el registro de prueba
        usuarioDAO.eliminar(
                usuario.getIdUsuario()
        );
    }

    @Test
    void debeRechazarUsuarioSinNombre() {

        // Creamos el DAO
        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        // Creamos el Service
        UsuarioService usuarioService =
                new UsuarioService(usuarioDAO);

        // Creamos el Controller
        UsuarioController controller =
                new UsuarioController(usuarioService);

        // Creamos un usuario sin nombre
        Usuario usuario = new Usuario();

        usuario.setNombreCompleto("");
        usuario.setUsuario("usuario_test");
        usuario.setContrasenaHash("hash");
        usuario.setIdRol(1);

        // Sin nombre no debe registrarse
        boolean resultado =
                controller.registrarUsuario(usuario);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarUsuarioSinRol() {

        // Creamos el DAO
        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        // Creamos el Service
        UsuarioService usuarioService =
                new UsuarioService(usuarioDAO);

        // Creamos el Controller
        UsuarioController controller =
                new UsuarioController(usuarioService);

        // Creamos un usuario sin rol válido
        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Test"
        );

        usuario.setUsuario(
                "usuario_test"
        );

        usuario.setContrasenaHash(
                "hash"
        );

        // ID de rol inválido
        usuario.setIdRol(0);

        // No debe permitir registrar
        boolean resultado =
                controller.registrarUsuario(usuario);

        assertFalse(resultado);
    }
}