package com.sivet.service;

import com.sivet.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermisoServiceTest {

    private final PermisoService permisoService =
            new PermisoService();

    @Test
    void administradorPuedeGestionarProductos() {

        Usuario usuario = crearUsuario(1);

        assertTrue(
                permisoService.puedeGestionarProductos(usuario)
        );
    }

    @Test
    void almaceneroPuedeGestionarProductos() {

        Usuario usuario = crearUsuario(3);

        assertTrue(
                permisoService.puedeGestionarProductos(usuario)
        );
    }

    @Test
    void vendedorNoPuedeGestionarProductos() {

        Usuario usuario = crearUsuario(2);

        assertFalse(
                permisoService.puedeGestionarProductos(usuario)
        );
    }

    @Test
    void administradorPuedeRealizarVentas() {

        Usuario usuario = crearUsuario(1);

        assertTrue(
                permisoService.puedeRealizarVentas(usuario)
        );
    }

    @Test
    void vendedorPuedeRealizarVentas() {

        Usuario usuario = crearUsuario(2);

        assertTrue(
                permisoService.puedeRealizarVentas(usuario)
        );
    }

    @Test
    void almaceneroNoPuedeRealizarVentas() {

        Usuario usuario = crearUsuario(3);

        assertFalse(
                permisoService.puedeRealizarVentas(usuario)
        );
    }

    @Test
    void administradorPuedeGestionarCompras() {

        Usuario usuario = crearUsuario(1);

        assertTrue(
                permisoService.puedeGestionarCompras(usuario)
        );
    }

    @Test
    void almaceneroPuedeGestionarCompras() {

        Usuario usuario = crearUsuario(3);

        assertTrue(
                permisoService.puedeGestionarCompras(usuario)
        );
    }

    @Test
    void vendedorNoPuedeGestionarCompras() {

        Usuario usuario = crearUsuario(2);

        assertFalse(
                permisoService.puedeGestionarCompras(usuario)
        );
    }

    @Test
    void administradorPuedeGestionarInventario() {

        Usuario usuario = crearUsuario(1);

        assertTrue(
                permisoService.puedeGestionarInventario(usuario)
        );
    }

    @Test
    void almaceneroPuedeGestionarInventario() {

        Usuario usuario = crearUsuario(3);

        assertTrue(
                permisoService.puedeGestionarInventario(usuario)
        );
    }

    @Test
    void vendedorNoPuedeGestionarInventario() {

        Usuario usuario = crearUsuario(2);

        assertFalse(
                permisoService.puedeGestionarInventario(usuario)
        );
    }

    @Test
    void usuarioNuloNoTienePermisos() {

        assertFalse(
                permisoService.puedeGestionarProductos(null)
        );

        assertFalse(
                permisoService.puedeRealizarVentas(null)
        );

        assertFalse(
                permisoService.puedeGestionarCompras(null)
        );

        assertFalse(
                permisoService.puedeGestionarInventario(null)
        );
    }

    private Usuario crearUsuario(int idRol) {

        return new Usuario(
                1,
                "Usuario de prueba",
                "usuario",
                "hash",
                idRol
        );
    }
}