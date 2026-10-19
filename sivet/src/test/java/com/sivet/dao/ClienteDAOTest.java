package com.sivet.dao;

import com.sivet.model.Cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para ClienteDAO
public class ClienteDAOTest {

    @Test
    void debeGuardarYBuscarCliente() {

        // Creamos el DAO
        ClienteDAO dao = new ClienteDAOImpl();

        // Generamos un DNI/RUC de exactamente 11 dígitos
        String dniRuc =
                String.valueOf(
                        70000000000L
                                + (System.currentTimeMillis()
                                % 10000000000L)
                );

        // Creamos el cliente
        Cliente cliente = new Cliente();

        cliente.setDniRuc(dniRuc);
        cliente.setNombres("Cliente de prueba");
        cliente.setTelefono("999888777");
        cliente.setDireccion("Direccion de prueba");

        // Guardamos el cliente
        dao.guardar(cliente);

        // Verificamos que PostgreSQL generó un ID
        assertTrue(cliente.getIdCliente() > 0);

        // Buscamos el cliente por su ID
        Cliente encontrado =
                dao.buscarPorId(cliente.getIdCliente());

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos el DNI/RUC
        assertEquals(
                dniRuc,
                encontrado.getDniRuc()
        );

        // Verificamos el nombre
        assertEquals(
                "Cliente de prueba",
                encontrado.getNombres()
        );

        // Eliminamos el cliente de prueba
        dao.eliminar(cliente.getIdCliente());
    }
}