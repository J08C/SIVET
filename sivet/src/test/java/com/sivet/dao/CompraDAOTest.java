package com.sivet.dao;

import com.sivet.model.Compra;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para CompraDAO
public class CompraDAOTest {

    @Test
    void debeGuardarYBuscarCompra() {

        // Creamos el DAO
        CompraDAO dao = new CompraDAOImpl();

        // Creamos la compra
        Compra compra = new Compra();

        compra.setFecha(LocalDateTime.now());
        compra.setTotal(150.00);

        // Usamos registros que ya existen en la base de datos
        compra.setIdUsuario(1);
        compra.setIdProveedor(1);

        // Guardamos
        dao.guardar(compra);

        // Verificamos ID generado
        assertTrue(compra.getIdCompra() > 0);

        // Buscamos la compra
        Compra encontrada =
                dao.buscarPorId(compra.getIdCompra());

        // Verificamos que existe
        assertNotNull(encontrada);

        // Verificamos datos
        assertEquals(
                150.00,
                encontrada.getTotal(),
                0.01
        );

        assertEquals(
                1,
                encontrada.getIdUsuario()
        );

        assertEquals(
                1,
                encontrada.getIdProveedor()
        );

        // Eliminamos la compra de prueba
        dao.eliminar(compra.getIdCompra());
    }
}