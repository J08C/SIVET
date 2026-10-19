package com.sivet.dao;

import com.sivet.model.Rol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para RolDAO
public class RolDAOTest {

    @Test
    void debeGuardarYBuscarRol() {

        // Creamos el DAO
        RolDAO dao = new RolDAOImpl();

        // Usamos un nombre único para no chocar
        // con los roles que ya existen en la base de datos
        String nombre = "RolTest_" + System.currentTimeMillis();

        // Creamos el objeto Rol
        Rol rol = new Rol();

        rol.setNombre(nombre);

        // Guardamos el rol
        dao.guardar(rol);

        // Verificamos que PostgreSQL generó un ID
        assertTrue(rol.getIdRol() > 0);

        // Buscamos nuevamente el rol
        Rol encontrado = dao.buscarPorId(rol.getIdRol());

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos que el nombre sea correcto
        assertEquals(nombre, encontrado.getNombre());

        // Eliminamos el registro de prueba
        dao.eliminar(rol.getIdRol());
    }
}