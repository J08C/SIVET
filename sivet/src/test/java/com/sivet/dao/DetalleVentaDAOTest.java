package com.sivet.dao;

import com.sivet.model.DetalleVenta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para DetalleVentaDAO
public class DetalleVentaDAOTest {

    @Test
    void debeGuardarYBuscarDetalleVenta() {

        // Creamos el DAO
        DetalleVentaDAO dao = new DetalleVentaDAOImpl();

        // Creamos el detalle
        DetalleVenta detalle = new DetalleVenta();

        detalle.setCantidad(2);
        detalle.setPrecioUnitario(9.50);
        detalle.setSubtotal(19.00);

        // Usamos registros existentes
        detalle.setIdVenta(1);
        detalle.setIdProducto(1);

        // Guardamos
        dao.guardar(detalle);

        // Verificamos ID generado
        assertTrue(
                detalle.getIdDetalleVenta() > 0
        );

        // Buscamos el detalle
        DetalleVenta encontrado =
                dao.buscarPorId(
                        detalle.getIdDetalleVenta()
                );

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(
                2,
                encontrado.getCantidad()
        );

        assertEquals(
                9.50,
                encontrado.getPrecioUnitario(),
                0.01
        );

        assertEquals(
                19.00,
                encontrado.getSubtotal(),
                0.01
        );

        // Eliminamos el detalle de prueba
        dao.eliminar(
                detalle.getIdDetalleVenta()
        );
    }
}