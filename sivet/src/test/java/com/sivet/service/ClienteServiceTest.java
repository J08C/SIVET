package com.sivet.service;

import com.sivet.dao.ClienteDAO;
import com.sivet.dao.ClienteDAOImpl;
import com.sivet.model.Cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para ClienteService
public class ClienteServiceTest {

    @Test
    void debeRegistrarClienteCorrectamente() {

        // Creamos el DAO
        ClienteDAO clienteDAO =
                new ClienteDAOImpl();

        // Creamos el Service
        ClienteService service =
                new ClienteService(clienteDAO);

        // Creamos un cliente de prueba
        Cliente cliente = new Cliente();

        cliente.setDniRuc(
                String.valueOf(
                        70000000000L
                                + (System.currentTimeMillis()
                                % 10000000000L)
                )
        );

        cliente.setNombres(
                "Cliente Test SIVET"
        );

        cliente.setTelefono("999999999");
        cliente.setDireccion("Direccion de prueba");

        // Ejecutamos el registro
        boolean resultado =
                service.registrarCliente(cliente);

        // Debe registrarse correctamente
        assertTrue(resultado);

        // PostgreSQL debe generar un ID
        assertTrue(
                cliente.getIdCliente() > 0
        );

        // Eliminamos el registro de prueba
        clienteDAO.eliminar(
                cliente.getIdCliente()
        );
    }

    @Test
    void debeRechazarClienteSinDniRuc() {

        ClienteDAO clienteDAO =
                new ClienteDAOImpl();

        ClienteService service =
                new ClienteService(clienteDAO);

        Cliente cliente = new Cliente();

        cliente.setDniRuc("");
        cliente.setNombres("Cliente Test");

        // Sin DNI/RUC no debe registrarse
        boolean resultado =
                service.registrarCliente(cliente);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarClienteSinNombre() {

        ClienteDAO clienteDAO =
                new ClienteDAOImpl();

        ClienteService service =
                new ClienteService(clienteDAO);

        Cliente cliente = new Cliente();

        cliente.setDniRuc("70000000001");
        cliente.setNombres("");

        // Sin nombre no debe registrarse
        boolean resultado =
                service.registrarCliente(cliente);

        assertFalse(resultado);
    }
}