package com.sivet.service;

import com.sivet.dao.CategoriaDAO;
import com.sivet.dao.CategoriaDAOImpl;
import com.sivet.model.Categoria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para CategoriaService
public class CategoriaServiceTest {

    @Test
    void debeRegistrarCategoriaCorrectamente() {

        // Creamos el DAO
        CategoriaDAO categoriaDAO =
                new CategoriaDAOImpl();

        // Creamos el Service
        CategoriaService service =
                new CategoriaService(categoriaDAO);

        // Creamos una categoría de prueba
        Categoria categoria = new Categoria();

        categoria.setNombre(
                "CategoriaTest_"
                        + System.currentTimeMillis()
        );

        // Ejecutamos el registro
        boolean resultado =
                service.registrarCategoria(categoria);

        // Debe registrarse correctamente
        assertTrue(resultado);

        // PostgreSQL debe generar un ID
        assertTrue(
                categoria.getIdCategoria() > 0
        );

        // Eliminamos el registro de prueba
        categoriaDAO.eliminar(
                categoria.getIdCategoria()
        );
    }

    @Test
    void debeRechazarCategoriaSinNombre() {

        CategoriaDAO categoriaDAO =
                new CategoriaDAOImpl();

        CategoriaService service =
                new CategoriaService(categoriaDAO);

        Categoria categoria = new Categoria();

        categoria.setNombre("");

        // Sin nombre no debe registrarse
        boolean resultado =
                service.registrarCategoria(categoria);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarCategoriaNula() {

        CategoriaDAO categoriaDAO =
                new CategoriaDAOImpl();

        CategoriaService service =
                new CategoriaService(categoriaDAO);

        // Una categoría nula no es válida
        boolean resultado =
                service.registrarCategoria(null);

        assertFalse(resultado);
    }
}