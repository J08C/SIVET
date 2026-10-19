package com.sivet.service;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.model.MovimientoInventario;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio de los movimientos de inventario
public class MovimientoInventarioService {

    private final MovimientoInventarioDAO movimientoInventarioDAO;

    public MovimientoInventarioService(
            MovimientoInventarioDAO movimientoInventarioDAO) {

        this.movimientoInventarioDAO =
                movimientoInventarioDAO;
    }

    // Registrar movimiento
    public boolean registrarMovimiento(
            MovimientoInventario movimiento) {

        if (movimiento == null) {
            return false;
        }

        if (movimiento.getTipo() == null
                || movimiento.getTipo().trim().isEmpty()) {
            return false;
        }

        if (!movimiento.getTipo().equals("ENTRADA")
                && !movimiento.getTipo().equals("SALIDA")) {
            return false;
        }

        if (movimiento.getCantidad() <= 0) {
            return false;
        }

        if (movimiento.getSaldo() < 0) {
            return false;
        }

        if (movimiento.getMotivo() == null
                || movimiento.getMotivo().trim().isEmpty()) {
            return false;
        }

        if (movimiento.getIdProducto() <= 0) {
            return false;
        }

        if (movimiento.getIdUsuario() <= 0) {
            return false;
        }

        if (movimientoInventarioDAO == null) {
            return false;
        }

        movimientoInventarioDAO.guardar(movimiento);

        return true;
    }

    // Listar todos los movimientos
    public List<MovimientoInventario> listarMovimientos() {

        if (movimientoInventarioDAO == null) {
            return new ArrayList<>();
        }

        return movimientoInventarioDAO.listar();
    }

    // Buscar movimiento por ID
    public MovimientoInventario buscarMovimientoPorId(
            int idMovimiento) {

        if (idMovimiento <= 0) {
            return null;
        }

        return movimientoInventarioDAO.buscarPorId(
                idMovimiento);
    }

    // Listar movimientos de un producto
    public List<MovimientoInventario> listarPorProducto(
            int idProducto) {

        if (idProducto <= 0) {
            return new ArrayList<>();
        }

        return movimientoInventarioDAO.listarPorProducto(
                idProducto);
    }

    // Actualizar movimiento
    public boolean actualizarMovimiento(
            MovimientoInventario movimiento) {

        if (movimiento == null) {
            return false;
        }

        if (movimiento.getIdMovimiento() <= 0) {
            return false;
        }

        if (movimiento.getTipo() == null
                || movimiento.getTipo().trim().isEmpty()) {
            return false;
        }

        if (!movimiento.getTipo().equals("ENTRADA")
                && !movimiento.getTipo().equals("SALIDA")) {
            return false;
        }

        if (movimiento.getCantidad() <= 0) {
            return false;
        }

        if (movimiento.getSaldo() < 0) {
            return false;
        }

        if (movimiento.getMotivo() == null
                || movimiento.getMotivo().trim().isEmpty()) {
            return false;
        }

        if (movimiento.getIdProducto() <= 0) {
            return false;
        }

        if (movimiento.getIdUsuario() <= 0) {
            return false;
        }

        MovimientoInventario existente =
                movimientoInventarioDAO.buscarPorId(
                        movimiento.getIdMovimiento());

        if (existente == null) {
            return false;
        }

        movimientoInventarioDAO.actualizar(movimiento);

        return true;
    }

    // Eliminar movimiento
    public boolean eliminarMovimiento(
            int idMovimiento) {

        if (idMovimiento <= 0) {
            return false;
        }

        MovimientoInventario existente =
                movimientoInventarioDAO.buscarPorId(
                        idMovimiento);

        if (existente == null) {
            return false;
        }

        movimientoInventarioDAO.eliminar(
                idMovimiento);

        return true;
    }
}