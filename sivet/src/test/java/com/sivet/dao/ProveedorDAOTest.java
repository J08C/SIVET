package com.sivet.dao;

import com.sivet.model.Proveedor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para ProveedorDAO
public class ProveedorDAOTest {

    @Test
    void debeGuardarYBuscarProveedor() {

        // Creamos el DAO
        ProveedorDAO dao = new ProveedorDAOImpl();

        // RUC único para evitar conflictos
        String ruc =
                "20" + String.valueOf(System.currentTimeMillis())
                        .substring(0, 9);

        // Creamos el proveedor
        Proveedor proveedor = new Proveedor();

        proveedor.setRuc(ruc);
        proveedor.setRazonSocial("Proveedor de prueba");
        proveedor.setTelefono("999999999");
        proveedor.setCorreo("proveedor@test.com");
        proveedor.setDireccion("Direccion de prueba");
        proveedor.setEstado(true);

        // Guardamos
        dao.guardar(proveedor);

        // Verificamos ID generado
        assertTrue(proveedor.getIdProveedor() > 0);

        // Buscamos el proveedor
        Proveedor encontrado =
                dao.buscarPorId(proveedor.getIdProveedor());

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(ruc, encontrado.getRuc());

        assertEquals(
                "Proveedor de prueba",
                encontrado.getRazonSocial()
        );

        // Eliminamos el registro de prueba
        dao.eliminar(proveedor.getIdProveedor());
    }
}