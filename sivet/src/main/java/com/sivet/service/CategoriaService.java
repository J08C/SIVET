package com.sivet.service;

import com.sivet.dao.CategoriaDAO;
import com.sivet.model.Categoria;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio relacionada con las categorías
public class CategoriaService {

    // Dependemos de la interfaz CategoriaDAO
    private final CategoriaDAO categoriaDAO;

    // Inyección de dependencia
    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // Registra una nueva categoría
    public boolean registrarCategoria(Categoria categoria) {

        // La categoría no puede ser nula
        if (categoria == null) {
            return false;
        }

        // El nombre es obligatorio
        if (categoria.getNombre() == null
                || categoria.getNombre().trim().isEmpty()) {
            return false;
        }

        categoriaDAO.guardar(categoria);

        return true;
    }

    // Lista todas las categorías
    public List<Categoria> listarCategorias() {

        if (categoriaDAO == null) {
            return new ArrayList<>();
        }

        return categoriaDAO.listar();
    }

    // Busca una categoría por su ID
    public Categoria buscarCategoriaPorId(
            int idCategoria) {

        if (idCategoria <= 0) {
            return null;
        }

        return categoriaDAO.buscarPorId(
                idCategoria
        );
    }

    // Actualiza una categoría
    public boolean actualizarCategoria(
            Categoria categoria) {

        if (categoria == null) {
            return false;
        }

        if (categoria.getIdCategoria() <= 0) {
            return false;
        }

        if (categoria.getNombre() == null
                || categoria.getNombre()
                .trim()
                .isEmpty()) {

            return false;
        }

        // Comprobamos que exista
        Categoria categoriaExistente =
                categoriaDAO.buscarPorId(
                        categoria.getIdCategoria()
                );

        if (categoriaExistente == null) {
            return false;
        }

        categoriaDAO.actualizar(categoria);

        return true;
    }

    // Elimina una categoría
    public boolean eliminarCategoria(
            int idCategoria) {

        if (idCategoria <= 0) {
            return false;
        }

        // Comprobamos que exista
        Categoria categoria =
                categoriaDAO.buscarPorId(
                        idCategoria
                );

        if (categoria == null) {
            return false;
        }

        categoriaDAO.eliminar(idCategoria);

        return true;
    }
}