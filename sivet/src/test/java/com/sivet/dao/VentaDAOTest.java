package com.sivet.dao;

import com.sivet.model.Venta;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para VentaDAO
public class VentaDAOTest {

    @Test
    void debeGuardarYBuscarVenta() {

        // Creamos el DAO
        VentaDAO dao = new VentaDAOImpl();

        // Creamos la venta
        Venta venta = new Venta();

        venta.setFecha(LocalDateTime.now());
        venta.setTotal(80.00);

        // Usuario existente
        venta.setIdUsuario(1);

        // Cliente existente
        venta.setIdCliente(1);

        // Guardamos
        dao.guardar(venta);

        // Verificamos ID generado
        assertTrue(
                venta.getIdVenta() > 0
        );

        // Buscamos la venta
        Venta encontrada =
                dao.buscarPorId(
                        venta.getIdVenta()
                );

        // Verificamos que existe
        assertNotNull(encontrada);

        // Verificamos datos
        assertEquals(
                80.00,
                encontrada.getTotal(),
                0.01
        );

        assertEquals(
                1,
                encontrada.getIdUsuario()
        );

        assertEquals(
                1,
                encontrada.getIdCliente()
        );

        // Eliminamos la venta de prueba
        dao.eliminar(
                venta.getIdVenta()
        );
    }
}