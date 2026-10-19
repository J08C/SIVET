package com.sivet.controller;

import com.sivet.model.Venta;
import com.sivet.service.VentaService;

import java.util.List;

// Controlador encargado de gestionar las operaciones de ventas
public class VentaController {

    private final VentaService ventaService;

    // Inyección de dependencia
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Verificar si se puede realizar una venta
    public boolean verificarVenta(
            int idProducto,
            int cantidadVenta) {

        return ventaService.puedeRealizarVenta(
                idProducto,
                cantidadVenta
        );
    }

    // Registrar venta
    public boolean registrarVenta(
            Venta venta) {

        return ventaService.registrarVenta(
                venta
        );
    }

    // Registrar venta y generar salida de inventario
    public boolean registrarVentaConSalida(
            Venta venta,
            int idProducto,
            int cantidad,
            String motivo) {

        return ventaService.registrarVentaConSalida(
                venta,
                idProducto,
                cantidad,
                motivo
        );
    }

    // Listar ventas
    public List<Venta> listarVentas() {

        return ventaService.listarVentas();
    }

    // Buscar venta por ID
    public Venta buscarVentaPorId(
            int idVenta) {

        return ventaService.buscarVentaPorId(
                idVenta
        );
    }

    // Actualizar venta
    public boolean actualizarVenta(
            Venta venta) {

        return ventaService.actualizarVenta(
                venta
        );
    }

    // Eliminar venta
    public boolean eliminarVenta(
            int idVenta) {

        return ventaService.eliminarVenta(
                idVenta
        );
    }
}