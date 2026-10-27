
package com.sivet.service;

import com.sivet.dao.ClienteDAO;
import com.sivet.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;

    // Inyección de dependencias
    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    // Registra un nuevo cliente
    public boolean registrarCliente(Cliente cliente) {

        if (cliente == null) {
            return false;
        }

        // El DNI/RUC es obligatorio
        if (cliente.getDniRuc() == null
                || cliente.getDniRuc().trim().isEmpty()) {
            return false;
        }

        // El DNI/RUC debe tener como máximo 11 caracteres
        if (cliente.getDniRuc().length() > 11) {
            return false;
        }

        // El nombre es obligatorio
        if (cliente.getNombres() == null
                || cliente.getNombres().trim().isEmpty()) {
            return false;
        }

        // Guardamos el cliente
        clienteDAO.guardar(cliente);

        return true;
    }

    // Lista todos los clientes
    public List<Cliente> listarClientes() {

        if (clienteDAO == null) {
            return new ArrayList<>();
        }

        return clienteDAO.listar();
    }

    // Busca un cliente por ID
    public Cliente buscarClientePorId(int idCliente) {

        if (idCliente <= 0) {
            return null;
        }

        return clienteDAO.buscarPorId(idCliente);
    }

    // Actualiza un cliente
    public boolean actualizarCliente(Cliente cliente) {

        if (cliente == null) {
            return false;
        }

        if (cliente.getIdCliente() <= 0) {
            return false;
        }

        // El DNI/RUC es obligatorio
        if (cliente.getDniRuc() == null
                || cliente.getDniRuc().trim().isEmpty()) {
            return false;
        }

        // El DNI/RUC debe tener como máximo 11 caracteres
        if (cliente.getDniRuc().length() > 11) {
            return false;
        }

        // El nombre es obligatorio
        if (cliente.getNombres() == null
                || cliente.getNombres().trim().isEmpty()) {
            return false;
        }

        // Comprobamos que el cliente exista
        Cliente clienteExistente =
                clienteDAO.buscarPorId(cliente.getIdCliente());

        if (clienteExistente == null) {
            return false;
        }

        clienteDAO.actualizar(cliente);

        return true;
    }

    // Elimina un cliente
    public boolean eliminarCliente(int idCliente) {

        if (idCliente <= 0) {
            return false;
        }

        // Comprobamos que el cliente exista
        Cliente cliente = clienteDAO.buscarPorId(idCliente);

        if (cliente == null) {
            return false;
        }

        clienteDAO.eliminar(idCliente);

        return true;
    }
}
