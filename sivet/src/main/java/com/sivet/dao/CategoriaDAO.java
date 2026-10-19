package com.sivet.dao;

import com.sivet.model.Categoria;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Categoria
public interface CategoriaDAO {

    // Registrar una categoria
    void guardar(Categoria categoria);

    // Buscar una categoria por su ID
    Categoria buscarPorId(int idCategoria);

    // Listar todas las categorias
    List<Categoria> listar();

    // Actualizar una categoria
    void actualizar(Categoria categoria);

    // Eliminar una categoria
    void eliminar(int idCategoria);
}