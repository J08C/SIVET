package com.sivet.dao;

import com.sivet.model.Venta;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Venta
public interface VentaDAO {

    // Registrar venta
    void guardar(Venta venta);

    // Buscar venta por ID
    Venta buscarPorId(int idVenta);

    // Listar ventas
    List<Venta> listar();

    // Actualizar venta
    void actualizar(Venta venta);

    // Eliminar venta
    void eliminar(int idVenta);
}