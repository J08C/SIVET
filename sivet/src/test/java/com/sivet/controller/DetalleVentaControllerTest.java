package com.sivet.controller;

import com.sivet.model.DetalleVenta;
import com.sivet.service.DetalleVentaService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleVentaControllerTest {

    @Test
    void debeRegistrarDetalle() {

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public boolean registrarDetalle(
                            DetalleVenta detalle) {
                        return true;
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        DetalleVenta detalle = new DetalleVenta();

        detalle.setCantidad(2);
        detalle.setPrecioUnitario(9.50);
        detalle.setSubtotal(19.00);
        detalle.setIdVenta(1);
        detalle.setIdProducto(1);

        boolean resultado =
                controller.registrarDetalle(detalle);

        assertTrue(resultado);
    }

    @Test
    void debeListarDetalles() {

        DetalleVenta detalle = new DetalleVenta();

        detalle.setIdDetalleVenta(1);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(9.50);
        detalle.setSubtotal(19.00);
        detalle.setIdVenta(1);
        detalle.setIdProducto(1);

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public List<DetalleVenta> listarDetalles() {
                        return List.of(detalle);
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        List<DetalleVenta> resultado =
                controller.listarDetalles();

        assertEquals(1, resultado.size());
        assertEquals(1,
                resultado.get(0).getIdDetalleVenta());
    }

    @Test
    void debeBuscarDetallePorId() {

        DetalleVenta detalle = new DetalleVenta();

        detalle.setIdDetalleVenta(1);

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public DetalleVenta buscarDetallePorId(
                            int idDetalleVenta) {
                        return detalle;
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        DetalleVenta resultado =
                controller.buscarDetallePorId(1);

        assertNotNull(resultado);
        assertEquals(1,
                resultado.getIdDetalleVenta());
    }

    @Test
    void debeListarDetallesPorVenta() {

        DetalleVenta detalle = new DetalleVenta();

        detalle.setIdDetalleVenta(1);
        detalle.setIdVenta(10);

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public List<DetalleVenta> listarDetallesPorVenta(
                            int idVenta) {
                        return List.of(detalle);
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        List<DetalleVenta> resultado =
                controller.listarDetallesPorVenta(10);

        assertEquals(1, resultado.size());
        assertEquals(10,
                resultado.get(0).getIdVenta());
    }

    @Test
    void debeActualizarDetalle() {

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public boolean actualizarDetalle(
                            DetalleVenta detalle) {
                        return true;
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        DetalleVenta detalle = new DetalleVenta();

        detalle.setIdDetalleVenta(1);
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(10);
        detalle.setSubtotal(30);
        detalle.setIdVenta(1);
        detalle.setIdProducto(1);

        boolean resultado =
                controller.actualizarDetalle(detalle);

        assertTrue(resultado);
    }

    @Test
    void debeEliminarDetalle() {

        DetalleVentaService service =
                new DetalleVentaService(null) {

                    @Override
                    public boolean eliminarDetalle(
                            int idDetalleVenta) {
                        return true;
                    }
                };

        DetalleVentaController controller =
                new DetalleVentaController(service);

        boolean resultado =
                controller.eliminarDetalle(1);

        assertTrue(resultado);
    }
}