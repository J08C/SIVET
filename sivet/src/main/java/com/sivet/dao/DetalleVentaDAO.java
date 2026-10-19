package com.sivet.dao;

import com.sivet.model.DetalleVenta;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de DetalleVenta
public interface DetalleVentaDAO {

    // Registrar detalle de venta
    void guardar(DetalleVenta detalle);

    // Buscar detalle por ID
    DetalleVenta buscarPorId(int idDetalleVenta);

    // Listar todos los detalles
    List<DetalleVenta> listar();

    // Buscar detalles de una venta
    List<DetalleVenta> listarPorVenta(int idVenta);

    // Actualizar detalle
    void actualizar(DetalleVenta detalle);

    // Eliminar detalle
    void eliminar(int idDetalleVenta);
}