package com.sivet.controller;

import com.sivet.model.Cliente;
import com.sivet.service.ClienteService;

import java.util.List;

// Controlador encargado de gestionar las operaciones de clientes
public class ClienteController {

    // Service que contiene la lógica de negocio
    private final ClienteService clienteService;

    // Inyección de dependencia
    public ClienteController(
            ClienteService clienteService) {

        this.clienteService = clienteService;
    }

    // Registra un nuevo cliente
    public boolean registrarCliente(
            Cliente cliente) {

        return clienteService.registrarCliente(
                cliente
        );
    }

    // Lista todos los clientes
    public List<Cliente> listarClientes() {

        return clienteService.listarClientes();
    }

    // Busca un cliente por ID
    public Cliente buscarClientePorId(
            int idCliente) {

        return clienteService.buscarClientePorId(
                idCliente
        );
    }

    // Actualiza un cliente
    public boolean actualizarCliente(
            Cliente cliente) {

        return clienteService.actualizarCliente(
                cliente
        );
    }

    // Elimina un cliente
    public boolean eliminarCliente(
            int idCliente) {

        return clienteService.eliminarCliente(
                idCliente
        );
    }
}