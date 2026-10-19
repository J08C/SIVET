package com.sivet.dao;

import com.sivet.model.Categoria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para CategoriaDAO
public class CategoriaDAOTest {

    @Test
    void debeGuardarYBuscarCategoria() {

        // Creamos el DAO
        CategoriaDAO dao = new CategoriaDAOImpl();

        // Nombre único para evitar conflictos
        String nombre =
                "CategoriaTest_" + System.currentTimeMillis();

        // Creamos la categoria
        Categoria categoria = new Categoria();

        categoria.setNombre(nombre);

        // Guardamos
        dao.guardar(categoria);

        // Verificamos que PostgreSQL generó ID
        assertTrue(categoria.getIdCategoria() > 0);

        // Buscamos la categoria
        Categoria encontrada =
                dao.buscarPorId(categoria.getIdCategoria());

        // Verificamos que existe
        assertNotNull(encontrada);

        // Verificamos el nombre
        assertEquals(nombre, encontrada.getNombre());

        // Eliminamos el registro de prueba
        dao.eliminar(categoria.getIdCategoria());
    }
}