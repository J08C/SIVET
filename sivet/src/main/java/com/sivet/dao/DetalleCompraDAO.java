package com.sivet.dao;

import com.sivet.model.DetalleCompra;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de DetalleCompra
public interface DetalleCompraDAO {

    // Registrar detalle de compra
    void guardar(DetalleCompra detalle);

    // Buscar detalle por ID
    DetalleCompra buscarPorId(int idDetalleCompra);

    // Listar todos los detalles
    List<DetalleCompra> listar();

    // Buscar detalles de una compra
    List<DetalleCompra> listarPorCompra(int idCompra);

    // Actualizar detalle
    void actualizar(DetalleCompra detalle);

    // Eliminar detalle
    void eliminar(int idDetalleCompra);
}