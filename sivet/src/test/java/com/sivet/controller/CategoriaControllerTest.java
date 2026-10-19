package com.sivet.controller;

import com.sivet.dao.CategoriaDAO;
import com.sivet.dao.CategoriaDAOImpl;
import com.sivet.model.Categoria;
import com.sivet.service.CategoriaService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para CategoriaController
public class CategoriaControllerTest {

    @Test
    void debeRegistrarCategoriaCorrectamente() {

        // Creamos el DAO
        CategoriaDAO categoriaDAO =
                new CategoriaDAOImpl();

        // Creamos el Service
        CategoriaService categoriaService =
                new CategoriaService(categoriaDAO);

        // Creamos el Controller
        CategoriaController controller =
                new CategoriaController(categoriaService);

        // Creamos una categoría de prueba
        Categoria categoria = new Categoria();

        categoria.setNombre(
                "CategoriaControllerTest_"
                        + System.currentTimeMillis()
        );

        // Registramos la categoría
        boolean resultado =
                controller.registrarCategoria(categoria);

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

        CategoriaService categoriaService =
                new CategoriaService(categoriaDAO);

        CategoriaController controller =
                new CategoriaController(categoriaService);

        Categoria categoria = new Categoria();

        categoria.setNombre("");

        // Sin nombre no debe registrarse
        boolean resultado =
                controller.registrarCategoria(categoria);

        assertFalse(resultado);
    }
}