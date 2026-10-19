package com.sivet.controller;

import com.sivet.model.DetalleVenta;
import com.sivet.service.DetalleVentaService;

import java.util.List;

// Controlador encargado de gestionar los detalles de venta
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    // Inyección de dependencia
    public DetalleVentaController(
            DetalleVentaService detalleVentaService) {

        this.detalleVentaService = detalleVentaService;
    }

    // Registrar detalle de venta
    public boolean registrarDetalle(
            DetalleVenta detalle) {

        return detalleVentaService.registrarDetalle(
                detalle
        );
    }

    // Listar todos los detalles
    public List<DetalleVenta> listarDetalles() {

        return detalleVentaService.listarDetalles();
    }

    // Buscar detalle por ID
    public DetalleVenta buscarDetallePorId(
            int idDetalleVenta) {

        return detalleVentaService.buscarDetallePorId(
                idDetalleVenta
        );
    }

    // Listar detalles de una venta
    public List<DetalleVenta> listarDetallesPorVenta(
            int idVenta) {

        return detalleVentaService.listarDetallesPorVenta(
                idVenta
        );
    }

    // Actualizar detalle
    public boolean actualizarDetalle(
            DetalleVenta detalle) {

        return detalleVentaService.actualizarDetalle(
                detalle
        );
    }

    // Eliminar detalle
    public boolean eliminarDetalle(
            int idDetalleVenta) {

        return detalleVentaService.eliminarDetalle(
                idDetalleVenta
        );
    }
}