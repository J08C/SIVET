package com.sivet.service;

import com.sivet.dao.DetalleCompraDAO;
import com.sivet.model.DetalleCompra;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio de los detalles de compra
public class DetalleCompraService {

    private final DetalleCompraDAO detalleCompraDAO;

    public DetalleCompraService(
            DetalleCompraDAO detalleCompraDAO) {

        this.detalleCompraDAO = detalleCompraDAO;
    }

    // Registrar detalle de compra
    public boolean registrarDetalle(
            DetalleCompra detalle) {

        if (detalle == null) {
            return false;
        }

        if (detalle.getCantidad() <= 0) {
            return false;
        }

        if (detalle.getCostoUnitario() < 0) {
            return false;
        }

        if (detalle.getSubtotal() < 0) {
            return false;
        }

        if (detalle.getIdCompra() <= 0) {
            return false;
        }

        if (detalle.getIdProducto() <= 0) {
            return false;
        }

        if (detalleCompraDAO == null) {
            return false;
        }

        detalleCompraDAO.guardar(detalle);

        return true;
    }

    // Listar todos los detalles
    public List<DetalleCompra> listarDetalles() {

        if (detalleCompraDAO == null) {
            return new ArrayList<>();
        }

        return detalleCompraDAO.listar();
    }

    // Buscar detalle por ID
    public DetalleCompra buscarDetallePorId(
            int idDetalleCompra) {

        if (idDetalleCompra <= 0) {
            return null;
        }

        return detalleCompraDAO.buscarPorId(
                idDetalleCompra);
    }

    // Listar detalles de una compra
    public List<DetalleCompra> listarDetallesPorCompra(
            int idCompra) {

        if (idCompra <= 0) {
            return new ArrayList<>();
        }

        return detalleCompraDAO.listarPorCompra(
                idCompra);
    }

    // Actualizar detalle
    public boolean actualizarDetalle(
            DetalleCompra detalle) {

        if (detalle == null) {
            return false;
        }

        if (detalle.getIdDetalleCompra() <= 0) {
            return false;
        }

        if (detalle.getCantidad() <= 0) {
            return false;
        }

        if (detalle.getCostoUnitario() < 0) {
            return false;
        }

        if (detalle.getSubtotal() < 0) {
            return false;
        }

        if (detalle.getIdCompra() <= 0) {
            return false;
        }

        if (detalle.getIdProducto() <= 0) {
            return false;
        }

        DetalleCompra existente =
                detalleCompraDAO.buscarPorId(
                        detalle.getIdDetalleCompra());

        if (existente == null) {
            return false;
        }

        detalleCompraDAO.actualizar(detalle);

        return true;
    }

    // Eliminar detalle
    public boolean eliminarDetalle(
            int idDetalleCompra) {

        if (idDetalleCompra <= 0) {
            return false;
        }

        DetalleCompra existente =
                detalleCompraDAO.buscarPorId(
                        idDetalleCompra);

        if (existente == null) {
            return false;
        }

        detalleCompraDAO.eliminar(
                idDetalleCompra);

        return true;
    }
}