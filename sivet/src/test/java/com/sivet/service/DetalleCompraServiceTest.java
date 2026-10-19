package com.sivet.service;

import com.sivet.dao.DetalleCompraDAO;
import com.sivet.model.DetalleCompra;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DetalleCompraServiceTest {

    // DAO simulado para las pruebas
    private DetalleCompraDAO crearDAO() {

        return new DetalleCompraDAO() {

            private final List<DetalleCompra> detalles =
                    new ArrayList<>();

            @Override
            public void guardar(DetalleCompra detalle) {

                detalle.setIdDetalleCompra(100);
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
    }


    @Test
    void debeRegistrarDetalleCorrectamente() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(5);
        detalle.setCostoUnitario(8.50);
        detalle.setSubtotal(42.50);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        boolean resultado =
                service.registrarDetalle(detalle);

        assertTrue(resultado);
        assertEquals(100,
                detalle.getIdDetalleCompra());
    }


    @Test
    void debeRechazarCantidadCero() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(0);
        detalle.setCostoUnitario(8.50);
        detalle.setSubtotal(0);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        boolean resultado =
                service.registrarDetalle(detalle);

        assertFalse(resultado);
    }


    @Test
    void debeRechazarCostoNegativo() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(5);
        detalle.setCostoUnitario(-8.50);
        detalle.setSubtotal(42.50);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        boolean resultado =
                service.registrarDetalle(detalle);

        assertFalse(resultado);
    }


    @Test
    void debeBuscarDetallePorId() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(3);
        detalle.setCostoUnitario(10);
        detalle.setSubtotal(30);
        detalle.setIdCompra(1);
        detalle.setIdProducto(2);

        service.registrarDetalle(detalle);

        DetalleCompra encontrado =
                service.buscarDetallePorId(
                        detalle.getIdDetalleCompra());

        assertNotNull(encontrado);
        assertEquals(3,
                encontrado.getCantidad());
        assertEquals(2,
                encontrado.getIdProducto());
    }


    @Test
    void debeListarDetallesPorCompra() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle1 =
                new DetalleCompra();

        detalle1.setCantidad(2);
        detalle1.setCostoUnitario(5);
        detalle1.setSubtotal(10);
        detalle1.setIdCompra(1);
        detalle1.setIdProducto(1);

        DetalleCompra detalle2 =
                new DetalleCompra();

        detalle2.setCantidad(4);
        detalle2.setCostoUnitario(7);
        detalle2.setSubtotal(28);
        detalle2.setIdCompra(1);
        detalle2.setIdProducto(2);

        service.registrarDetalle(detalle1);
        service.registrarDetalle(detalle2);

        List<DetalleCompra> resultado =
                service.listarDetallesPorCompra(1);

        assertEquals(2, resultado.size());
    }


    @Test
    void debeActualizarDetalle() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(2);
        detalle.setCostoUnitario(5);
        detalle.setSubtotal(10);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        service.registrarDetalle(detalle);

        detalle.setCantidad(10);
        detalle.setSubtotal(50);

        boolean resultado =
                service.actualizarDetalle(detalle);

        assertTrue(resultado);
        assertEquals(10,
                detalle.getCantidad());
        assertEquals(50,
                detalle.getSubtotal());
    }


    @Test
    void debeEliminarDetalle() {

        DetalleCompraDAO dao = crearDAO();

        DetalleCompraService service =
                new DetalleCompraService(dao);

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(2);
        detalle.setCostoUnitario(5);
        detalle.setSubtotal(10);
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        service.registrarDetalle(detalle);

        int id =
                detalle.getIdDetalleCompra();

        boolean resultado =
                service.eliminarDetalle(id);

        assertTrue(resultado);

        assertNull(
                service.buscarDetallePorId(id));
    }
}