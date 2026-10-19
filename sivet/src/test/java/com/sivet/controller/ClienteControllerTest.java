package com.sivet.controller;

import com.sivet.dao.ClienteDAO;
import com.sivet.dao.ClienteDAOImpl;
import com.sivet.model.Cliente;
import com.sivet.service.ClienteService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para ClienteController
public class ClienteControllerTest {

    @Test
    void debeRegistrarClienteCorrectamente() {

        // Creamos el DAO
        ClienteDAO clienteDAO =
                new ClienteDAOImpl();

        // Creamos el Service
        ClienteService clienteService =
                new ClienteService(clienteDAO);

        // Creamos el Controller
        ClienteController controller =
                new ClienteController(clienteService);

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
                "Cliente Controller Test"
        );

        cliente.setTelefono("999999999");
        cliente.setDireccion("Direccion de prueba");

        // Registramos el cliente
        boolean resultado =
                controller.registrarCliente(cliente);

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

        ClienteService clienteService =
                new ClienteService(clienteDAO);

        ClienteController controller =
                new ClienteController(clienteService);

        Cliente cliente = new Cliente();

        cliente.setDniRuc("");
        cliente.setNombres(
                "Cliente Test"
        );

        // Sin DNI/RUC no debe registrarse
        boolean resultado =
                controller.registrarCliente(cliente);

        assertFalse(resultado);
    }
}