package com.sivet.service;

import com.sivet.dao.DetalleVentaDAO;
import com.sivet.model.DetalleVenta;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio relacionada con los detalles de venta
public class DetalleVentaService {

    private final DetalleVentaDAO detalleVentaDAO;

    public DetalleVentaService(
            DetalleVentaDAO detalleVentaDAO) {

        this.detalleVentaDAO = detalleVentaDAO;
    }

    // Registrar detalle
    public boolean registrarDetalle(
            DetalleVenta detalle) {

        if (detalle == null) return false;

        if (detalle.getCantidad() <= 0) return false;

        if (detalle.getPrecioUnitario() < 0) return false;

        if (detalle.getSubtotal() < 0) return false;

        if (detalle.getIdVenta() <= 0) return false;

        if (detalle.getIdProducto() <= 0) return false;

        if (detalleVentaDAO == null) return false;

        detalleVentaDAO.guardar(detalle);

        return true;
    }

    // Listar todos los detalles
    public List<DetalleVenta> listarDetalles() {

        if (detalleVentaDAO == null) {
            return new ArrayList<>();
        }

        return detalleVentaDAO.listar();
    }

    // Buscar detalle por ID
    public DetalleVenta buscarDetallePorId(
            int idDetalleVenta) {

        if (idDetalleVenta <= 0) {
            return null;
        }

        return detalleVentaDAO.buscarPorId(
                idDetalleVenta
        );
    }

    // Listar detalles de una venta
    public List<DetalleVenta> listarDetallesPorVenta(
            int idVenta) {

        if (idVenta <= 0) {
            return new ArrayList<>();
        }

        return detalleVentaDAO.listarPorVenta(
                idVenta
        );
    }

    // Actualizar detalle
    public boolean actualizarDetalle(
            DetalleVenta detalle) {

        if (detalle == null) return false;

        if (detalle.getIdDetalleVenta() <= 0) {
            return false;
        }

        if (detalle.getCantidad() <= 0) {
            return false;
        }

        if (detalle.getPrecioUnitario() < 0) {
            return false;
        }

        if (detalle.getSubtotal() < 0) {
            return false;
        }

        if (detalle.getIdVenta() <= 0) {
            return false;
        }

        if (detalle.getIdProducto() <= 0) {
            return false;
        }

        DetalleVenta existente =
                detalleVentaDAO.buscarPorId(
                        detalle.getIdDetalleVenta()
                );

        if (existente == null) {
            return false;
        }

        detalleVentaDAO.actualizar(detalle);

        return true;
    }

    // Eliminar detalle
    public boolean eliminarDetalle(
            int idDetalleVenta) {

        if (idDetalleVenta <= 0) {
            return false;
        }

        DetalleVenta existente =
                detalleVentaDAO.buscarPorId(
                        idDetalleVenta
                );

        if (existente == null) {
            return false;
        }

        detalleVentaDAO.eliminar(
                idDetalleVenta
        );

        return true;
    }
}