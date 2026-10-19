package com.sivet.dao;

import com.sivet.model.DetalleCompra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para DetalleCompraDAO
public class DetalleCompraDAOTest {

    @Test
    void debeGuardarYBuscarDetalleCompra() {

        // Creamos el DAO
        DetalleCompraDAO dao = new DetalleCompraDAOImpl();

        // Creamos el detalle
        DetalleCompra detalle = new DetalleCompra();

        detalle.setCantidad(5);
        detalle.setCostoUnitario(10.00);
        detalle.setSubtotal(50.00);

        // Usamos registros existentes
        detalle.setIdCompra(1);
        detalle.setIdProducto(1);

        // Guardamos
        dao.guardar(detalle);

        // Verificamos ID generado
        assertTrue(
                detalle.getIdDetalleCompra() > 0
        );

        // Buscamos el detalle
        DetalleCompra encontrado =
                dao.buscarPorId(
                        detalle.getIdDetalleCompra()
                );

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(
                5,
                encontrado.getCantidad()
        );

        assertEquals(
                10.00,
                encontrado.getCostoUnitario(),
                0.01
        );

        assertEquals(
                50.00,
                encontrado.getSubtotal(),
                0.01
        );

        // Eliminamos el detalle de prueba
        dao.eliminar(
                detalle.getIdDetalleCompra()
        );
    }
}