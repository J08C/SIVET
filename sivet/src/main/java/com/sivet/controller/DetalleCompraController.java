package com.sivet.controller;

import com.sivet.model.DetalleCompra;
import com.sivet.service.DetalleCompraService;

import java.util.List;

// Controlador encargado de gestionar los detalles de compra
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(
            DetalleCompraService detalleCompraService) {

        this.detalleCompraService = detalleCompraService;
    }

    public boolean registrarDetalle(
            DetalleCompra detalle) {

        return detalleCompraService.registrarDetalle(
                detalle);
    }

    public List<DetalleCompra> listarDetalles() {

        return detalleCompraService.listarDetalles();
    }

    public DetalleCompra buscarDetallePorId(
            int idDetalleCompra) {

        return detalleCompraService.buscarDetallePorId(
                idDetalleCompra);
    }

    public List<DetalleCompra> listarDetallesPorCompra(
            int idCompra) {

        return detalleCompraService
                .listarDetallesPorCompra(idCompra);
    }

    public boolean actualizarDetalle(
            DetalleCompra detalle) {

        return detalleCompraService.actualizarDetalle(
                detalle);
    }

    public boolean eliminarDetalle(
            int idDetalleCompra) {

        return detalleCompraService.eliminarDetalle(
                idDetalleCompra);
    }
}