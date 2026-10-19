package com.sivet.dao;

import com.sivet.model.MovimientoInventario;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos
// de MovimientoInventario
public interface MovimientoInventarioDAO {

    // Registrar movimiento
    void guardar(MovimientoInventario movimiento);

    // Buscar movimiento por ID
    MovimientoInventario buscarPorId(int idMovimiento);

    // Listar todos los movimientos
    List<MovimientoInventario> listar();

    // Buscar movimientos de un producto
    List<MovimientoInventario> listarPorProducto(int idProducto);

    // Actualizar movimiento
    void actualizar(MovimientoInventario movimiento);

    // Eliminar movimiento
    void eliminar(int idMovimiento);
}