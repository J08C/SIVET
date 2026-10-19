package com.sivet.controller;

import com.sivet.model.Categoria;
import com.sivet.service.CategoriaService;

import java.util.List;

// Controlador encargado de gestionar las operaciones de categorías
public class CategoriaController {

    // Service que contiene la lógica de negocio
    private final CategoriaService categoriaService;

    // Inyección de dependencia
    public CategoriaController(
            CategoriaService categoriaService) {

        this.categoriaService = categoriaService;
    }

    // Registra una nueva categoría
    public boolean registrarCategoria(
            Categoria categoria) {

        return categoriaService.registrarCategoria(
                categoria
        );
    }

    // Lista todas las categorías
    public List<Categoria> listarCategorias() {

        return categoriaService.listarCategorias();
    }

    // Busca una categoría por ID
    public Categoria buscarCategoriaPorId(
            int idCategoria) {

        return categoriaService.buscarCategoriaPorId(
                idCategoria
        );
    }

    // Actualiza una categoría
    public boolean actualizarCategoria(
            Categoria categoria) {

        return categoriaService.actualizarCategoria(
                categoria
        );
    }

    // Elimina una categoría
    public boolean eliminarCategoria(
            int idCategoria) {

        return categoriaService.eliminarCategoria(
                idCategoria
        );
    }
}