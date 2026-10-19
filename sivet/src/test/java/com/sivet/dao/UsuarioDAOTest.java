package com.sivet.dao;

import com.sivet.model.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para UsuarioDAO
public class UsuarioDAOTest {

    @Test
    void debeGuardarYBuscarUsuario() {

        // Creamos el DAO
        UsuarioDAO dao = new UsuarioDAOImpl();

        // Nombre de usuario único
        String nombreUsuario =
                "usuario_test_" + System.currentTimeMillis();

        // Creamos el usuario
        Usuario usuario = new Usuario();

        usuario.setNombreCompleto("Usuario de prueba");
        usuario.setUsuario(nombreUsuario);
        usuario.setContrasenaHash("hash_de_prueba");
        usuario.setIdRol(1);

        // Guardamos
        dao.guardar(usuario);

        // Verificamos ID generado
        assertTrue(usuario.getIdUsuario() > 0);

        // Buscamos por nombre de usuario
        Usuario encontrado =
                dao.buscarPorUsuario(nombreUsuario);

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(
                nombreUsuario,
                encontrado.getUsuario()
        );

        assertEquals(
                "Usuario de prueba",
                encontrado.getNombreCompleto()
        );

        // Eliminamos el registro de prueba
        dao.eliminar(usuario.getIdUsuario());
    }
}