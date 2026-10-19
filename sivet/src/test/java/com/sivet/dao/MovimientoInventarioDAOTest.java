package com.sivet.dao;

import com.sivet.model.MovimientoInventario;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para MovimientoInventarioDAO
public class MovimientoInventarioDAOTest {

    @Test
    void debeGuardarYBuscarMovimiento() {

        // Creamos el DAO
        MovimientoInventarioDAO dao =
                new MovimientoInventarioDAOImpl();

        // Creamos el movimiento
        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(10);
        movimiento.setSaldo(40);
        movimiento.setMotivo("Movimiento de prueba");

        // Usamos registros existentes
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        // Guardamos
        dao.guardar(movimiento);

        // Verificamos ID generado
        assertTrue(
                movimiento.getIdMovimiento() > 0
        );

        // Buscamos el movimiento
        MovimientoInventario encontrado =
                dao.buscarPorId(
                        movimiento.getIdMovimiento()
                );

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(
                "ENTRADA",
                encontrado.getTipo()
        );

        assertEquals(
                10,
                encontrado.getCantidad()
        );

        assertEquals(
                40,
                encontrado.getSaldo()
        );

        assertEquals(
                "Movimiento de prueba",
                encontrado.getMotivo()
        );

        // Eliminamos el movimiento de prueba
        dao.eliminar(
                movimiento.getIdMovimiento()
        );
    }
}