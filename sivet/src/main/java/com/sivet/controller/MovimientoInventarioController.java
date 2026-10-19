package com.sivet.controller;

import com.sivet.model.MovimientoInventario;
import com.sivet.service.MovimientoInventarioService;

import java.util.List;

// Controlador encargado de gestionar los movimientos de inventario
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;

    public MovimientoInventarioController(
            MovimientoInventarioService movimientoInventarioService) {

        this.movimientoInventarioService =
                movimientoInventarioService;
    }

    public boolean registrarMovimiento(
            MovimientoInventario movimiento) {

        return movimientoInventarioService
                .registrarMovimiento(movimiento);
    }

    public List<MovimientoInventario> listarMovimientos() {

        return movimientoInventarioService
                .listarMovimientos();
    }

    public MovimientoInventario buscarMovimientoPorId(
            int idMovimiento) {

        return movimientoInventarioService
                .buscarMovimientoPorId(idMovimiento);
    }

    public List<MovimientoInventario> listarPorProducto(
            int idProducto) {

        return movimientoInventarioService
                .listarPorProducto(idProducto);
    }

    public boolean actualizarMovimiento(
            MovimientoInventario movimiento) {

        return movimientoInventarioService
                .actualizarMovimiento(movimiento);
    }

    public boolean eliminarMovimiento(
            int idMovimiento) {

        return movimientoInventarioService
                .eliminarMovimiento(idMovimiento);
    }
}