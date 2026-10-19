package com.sivet.dao;

import com.sivet.model.Compra;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Compra
public interface CompraDAO {

    // Registrar compra
    void guardar(Compra compra);

    // Buscar compra por ID
    Compra buscarPorId(int idCompra);

    // Listar compras
    List<Compra> listar();

    // Actualizar compra
    void actualizar(Compra compra);

    // Eliminar compra
    void eliminar(int idCompra);
}