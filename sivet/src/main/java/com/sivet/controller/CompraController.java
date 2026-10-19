package com.sivet.controller;

import com.sivet.model.Compra;
import com.sivet.service.CompraService;

import java.util.List;

// Controlador encargado de gestionar las compras
public class CompraController {

    private final CompraService compraService;

    public CompraController(
            CompraService compraService) {

        this.compraService = compraService;
    }

    // Registrar una compra
    public boolean registrarCompra(
            Compra compra) {

        return compraService.registrarCompra(compra);
    }

    // Registrar una compra y generar entrada de inventario
    public boolean registrarCompraConEntrada(
            Compra compra,
            int idProducto,
            int cantidad,
            String motivo) {

        return compraService.registrarCompraConEntrada(
                compra,
                idProducto,
                cantidad,
                motivo
        );
    }

    // Aumentar stock
    public boolean aumentarStock(
            int idProducto,
            int cantidadCompra) {

        return compraService.aumentarStock(
                idProducto,
                cantidadCompra
        );
    }

    // Listar compras
    public List<Compra> listarCompras() {

        return compraService.listarCompras();
    }

    // Buscar compra por ID
    public Compra buscarCompraPorId(
            int idCompra) {

        return compraService.buscarCompraPorId(idCompra);
    }

    // Actualizar compra
    public boolean actualizarCompra(
            Compra compra) {

        return compraService.actualizarCompra(compra);
    }

    // Eliminar compra
    public boolean eliminarCompra(
            int idCompra) {

        return compraService.eliminarCompra(idCompra);
    }
}