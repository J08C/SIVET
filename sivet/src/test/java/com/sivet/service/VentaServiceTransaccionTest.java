package com.sivet.service;

import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.VentaDAOImpl;
import com.sivet.model.Producto;
import com.sivet.model.Venta;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VentaServiceTransaccionTest {

    @Test
    void debeRegistrarVentaYDescontarStock() {

        ProductoDAOImpl productoDAO =
                new ProductoDAOImpl();

        VentaDAOImpl ventaDAO =
                new VentaDAOImpl();

        MovimientoInventarioDAOImpl movimientoDAO =
                new MovimientoInventarioDAOImpl();

        InventarioService inventarioService =
                new InventarioService(
                        movimientoDAO,
                        productoDAO
                );

        VentaService ventaService =
                new VentaService(
                        ventaDAO,
                        productoDAO,
                        inventarioService
                );

        // Buscar un producto que tenga stock disponible
        Producto productoAntes = productoDAO.listar()
                .stream()
                .filter(p -> p.getStockActual() >= 1)
                .findFirst()
                .orElse(null);

        assertNotNull(
                productoAntes,
                "Debe existir al menos un producto con stock."
        );

        int idProducto =
                productoAntes.getIdProducto();

        int stockAntes =
                productoAntes.getStockActual();

        int cantidad = 1;

        assertTrue(
                stockAntes >= cantidad,
                "El producto debe tener stock suficiente."
        );

        Venta venta = new Venta();

        venta.setFecha(LocalDateTime.now());

        venta.setTotal(
                productoAntes.getPrecioVenta()
        );

        venta.setIdUsuario(1);

        venta.setIdCliente(1);

        boolean resultado =
                ventaService.registrarVentaConSalida(
                        venta,
                        idProducto,
                        cantidad,
                        "Prueba transacción ACID"
                );

        assertTrue(
                resultado,
                "La venta debería registrarse correctamente."
        );

        assertTrue(
                venta.getIdVenta() > 0,
                "La venta debería obtener un ID."
        );

        Producto productoDespues =
                productoDAO.buscarPorId(idProducto);

        assertNotNull(productoDespues);

        assertEquals(
                stockAntes - cantidad,
                productoDespues.getStockActual()
        );
    }
}