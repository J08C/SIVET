package com.sivet.service;

import com.sivet.dao.UsuarioDAO;
import com.sivet.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void debeIniciarSesionConCredencialesCorrectas() {

        Usuario usuario = new Usuario(
                1,
                "Administrador",
                "admin",
                new PasswordService().generarHash("123456"),
                1
        );

        UsuarioDAO usuarioDAO = new UsuarioDAO() {

            @Override
            public void guardar(Usuario usuario) {
            }

            @Override
            public Usuario buscarPorId(int idUsuario) {
                return usuario;
            }

            @Override
            public Usuario buscarPorUsuario(String nombreUsuario) {

                if ("admin".equals(nombreUsuario)) {
                    return usuario;
                }

                return null;
            }

            @Override
            public java.util.List<Usuario> listar() {
                return java.util.List.of(usuario);
            }

            @Override
            public void actualizar(Usuario usuario) {
            }

            @Override
            public void eliminar(int idUsuario) {
            }
        };

        AuthService authService =
                new AuthService(
                        usuarioDAO,
                        new PasswordService()
                );

        Usuario resultado =
                authService.iniciarSesion(
                        "admin",
                        "123456"
                );

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsuario());
        assertEquals(1, resultado.getIdRol());
    }

    @Test
    void debeRechazarContrasenaIncorrecta() {

        Usuario usuario = new Usuario(
                1,
                "Administrador",
                "admin",
                new PasswordService().generarHash("123456"),
                1
        );

        UsuarioDAO usuarioDAO = new UsuarioDAO() {

            @Override
            public void guardar(Usuario usuario) {
            }

            @Override
            public Usuario buscarPorId(int idUsuario) {
                return usuario;
            }

            @Override
            public Usuario buscarPorUsuario(String nombreUsuario) {
                return usuario;
            }

            @Override
            public java.util.List<Usuario> listar() {
                return java.util.List.of(usuario);
            }

            @Override
            public void actualizar(Usuario usuario) {
            }

            @Override
            public void eliminar(int idUsuario) {
            }
        };

        AuthService authService =
                new AuthService(
                        usuarioDAO,
                        new PasswordService()
                );

        Usuario resultado =
                authService.iniciarSesion(
                        "admin",
                        "999999"
                );

        assertNull(resultado);
    }

    @Test
    void debeRechazarUsuarioInexistente() {

        UsuarioDAO usuarioDAO = new UsuarioDAO() {

            @Override
            public void guardar(Usuario usuario) {
            }

            @Override
            public Usuario buscarPorId(int idUsuario) {
                return null;
            }

            @Override
            public Usuario buscarPorUsuario(String nombreUsuario) {
                return null;
            }

            @Override
            public java.util.List<Usuario> listar() {
                return java.util.List.of();
            }

            @Override
            public void actualizar(Usuario usuario) {
            }

            @Override
            public void eliminar(int idUsuario) {
            }
        };

        AuthService authService =
                new AuthService(
                        usuarioDAO,
                        new PasswordService()
                );

        Usuario resultado =
                authService.iniciarSesion(
                        "no_existe",
                        "123456"
                );

        assertNull(resultado);
    }

    @Test
    void debeRechazarUsuarioVacio() {

        UsuarioDAO usuarioDAO = new UsuarioDAO() {

            @Override
            public void guardar(Usuario usuario) {
            }

            @Override
            public Usuario buscarPorId(int idUsuario) {
                return null;
            }

            @Override
            public Usuario buscarPorUsuario(String usuario) {
                return null;
            }

            @Override
            public java.util.List<Usuario> listar() {
                return java.util.List.of();
            }

            @Override
            public void actualizar(Usuario usuario) {
            }

            @Override
            public void eliminar(int idUsuario) {
            }
        };

        AuthService authService =
                new AuthService(
                        usuarioDAO,
                        new PasswordService()
                );

        Usuario resultado =
                authService.iniciarSesion(
                        "",
                        "123456"
                );

        assertNull(resultado);
    }

    @Test
    void debeReconocerRolAdministrador() {

        Usuario usuario = new Usuario(
                1,
                "Administrador",
                "admin",
                "hash",
                1
        );

        AuthService authService =
                new AuthService(
                        null,
                        new PasswordService()
                );

        assertTrue(
                authService.tieneRol(usuario, 1)
        );
    }

    @Test
    void debeRechazarRolIncorrecto() {

        Usuario usuario = new Usuario(
                1,
                "Administrador",
                "admin",
                "hash",
                1
        );

        AuthService authService =
                new AuthService(
                        null,
                        new PasswordService()
                );

        assertFalse(
                authService.tieneRol(usuario, 2)
        );
    }
}