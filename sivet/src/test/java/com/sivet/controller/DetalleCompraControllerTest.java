package com.sivet.controller;

import com.sivet.dao.DetalleCompraDAO;
import com.sivet.model.DetalleCompra;
import com.sivet.service.DetalleCompraService;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleCompraControllerTest {

    private DetalleCompraController crearController() {

        DetalleCompraDAO dao = new DetalleCompraDAO() {

            private final List<DetalleCompra> detalles =
                    new ArrayList<>();

            @Override
            public void guardar(DetalleCompra detalle) {
                detalle.setIdDetalleCompra(200);
                detalles.add(detalle);
            }

            @Override
            public DetalleCompra buscarPorId(
                    int idDetalleCompra) {

                for (DetalleCompra detalle : detalles) {
                    if (detalle.getIdDetalleCompra()
                            == idDetalleCompra) {
                        return detalle;
                    }
                }

                return null;
            }

            @Override
            public List<DetalleCompra> listar() {
                return detalles;
            }

            @Override
            public List<DetalleCompra> listarPorCompra(
                    int idCompra) {

                List<DetalleCompra> resultado =
                        new ArrayList<>();

                for (DetalleCompra detalle : detalles) {
                    if (detalle.getIdCompra() == idCompra) {
                        resultado.add(detalle);
                    }
                }

                return resultado;
            }

            @Override
            public void actualizar(
                    DetalleCompra detalle) {

                for (int i = 0;
                     i < detalles.size();
                     i++) {

                    if (detalles.get(i)
                            .getIdDetalleCompra()
                            == detalle.getIdDetalleCompra()) {

                        detalles.set(i, detalle);
                        return;
                    }
                }
            }

            @Override
            public void eliminar(
                    int idDetalleCompra) {

                detalles.removeIf(detalle ->
                        detalle.getIdDetalleCompra()
                                == idDetalleCompra);
            }
        };

        DetalleCompraService service =
                new DetalleCompraService(dao);

        return new DetalleCompraController(service);
    }


    @Test
    void debeRegistrarDetalle() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(5);
        detalle.setCostoUnitario(8.50);
        detalle.setSubtotal(42.50);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        boolean resultado =
                controller.registrarDetalle(detalle);

        assertTrue(resultado);
        assertEquals(
                200,
                detalle.getIdDetalleCompra());
    }


    @Test
    void debeListarDetalles() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(3);
        detalle.setCostoUnitario(10);
        detalle.setSubtotal(30);
        detalle.setIdCompra(1);
        detalle.setIdProducto(2);

        controller.registrarDetalle(detalle);

        List<DetalleCompra> resultado =
                controller.listarDetalles();

        assertEquals(1, resultado.size());
    }


    @Test
    void debeBuscarDetalle() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(2);
        detalle.setCostoUnitario(5);
        detalle.setSubtotal(10);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        controller.registrarDetalle(detalle);

        DetalleCompra encontrado =
                controller.buscarDetallePorId(
                        detalle.getIdDetalleCompra());

        assertNotNull(encontrado);
        assertEquals(
                2,
                encontrado.getCantidad());
    }


    @Test
    void debeListarDetallesPorCompra() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(4);
        detalle.setCostoUnitario(7);
        detalle.setSubtotal(28);
        detalle.setIdCompra(5);
        detalle.setIdProducto(2);

        controller.registrarDetalle(detalle);

        List<DetalleCompra> resultado =
                controller.listarDetallesPorCompra(5);

        assertEquals(1, resultado.size());
    }


    @Test
    void debeActualizarDetalle() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(2);
        detalle.setCostoUnitario(5);
        detalle.setSubtotal(10);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        controller.registrarDetalle(detalle);

        detalle.setCantidad(8);
        detalle.setSubtotal(40);

        boolean resultado =
                controller.actualizarDetalle(detalle);

        assertTrue(resultado);
        assertEquals(
                8,
                detalle.getCantidad());
    }


    @Test
    void debeEliminarDetalle() {

        DetalleCompraController controller =
                crearController();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(2);
        detalle.setCostoUnitario(5);
        detalle.setSubtotal(10);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        controller.registrarDetalle(detalle);

        int id =
                detalle.getIdDetalleCompra();

        boolean resultado =
                controller.eliminarDetalle(id);

        assertTrue(resultado);

        assertNull(
                controller.buscarDetallePorId(id));
    }
}