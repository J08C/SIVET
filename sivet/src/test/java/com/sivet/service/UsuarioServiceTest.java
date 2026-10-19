package com.sivet.service;

import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.model.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para UsuarioService
public class UsuarioServiceTest {

    @Test
    void debeRegistrarUsuarioCorrectamente() {

        // Creamos el DAO
        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        // Creamos el Service
        UsuarioService service =
                new UsuarioService(usuarioDAO);

        // Creamos un usuario de prueba
        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Test SIVET"
        );

        usuario.setUsuario(
                "usuario_test_"
                        + System.currentTimeMillis()
        );

        // Por ahora usamos un hash de prueba.
        // BCrypt lo implementaremos en autenticación.
        usuario.setContrasenaHash(
                "hash_de_prueba"
        );

        // Usamos el rol existente con ID 1
        usuario.setIdRol(1);

        // Ejecutamos el registro
        boolean resultado =
                service.registrarUsuario(usuario);

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

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        UsuarioService service =
                new UsuarioService(usuarioDAO);

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto("");
        usuario.setUsuario("usuario_test");
        usuario.setContrasenaHash("hash");
        usuario.setIdRol(1);

        // Sin nombre completo no debe registrarse
        boolean resultado =
                service.registrarUsuario(usuario);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarUsuarioSinNombreDeUsuario() {

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        UsuarioService service =
                new UsuarioService(usuarioDAO);

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Test"
        );

        usuario.setUsuario("");
        usuario.setContrasenaHash("hash");
        usuario.setIdRol(1);

        // Sin usuario no debe registrarse
        boolean resultado =
                service.registrarUsuario(usuario);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarUsuarioSinContrasena() {

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        UsuarioService service =
                new UsuarioService(usuarioDAO);

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Test"
        );

        usuario.setUsuario("usuario_test");
        usuario.setContrasenaHash("");
        usuario.setIdRol(1);

        // Sin contraseña no debe registrarse
        boolean resultado =
                service.registrarUsuario(usuario);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarUsuarioSinRol() {

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        UsuarioService service =
                new UsuarioService(usuarioDAO);

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                "Usuario Test"
        );

        usuario.setUsuario("usuario_test");
        usuario.setContrasenaHash("hash");

        // ID de rol inválido
        usuario.setIdRol(0);

        // Sin rol válido no debe registrarse
        boolean resultado =
                service.registrarUsuario(usuario);

        assertFalse(resultado);
    }
}