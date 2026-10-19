package com.sivet.dao;

import com.sivet.model.Cliente;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Cliente
public interface ClienteDAO {

    // Registrar cliente
    void guardar(Cliente cliente);

    // Buscar cliente por ID
    Cliente buscarPorId(int idCliente);

    // Listar clientes
    List<Cliente> listar();

    // Actualizar cliente
    void actualizar(Cliente cliente);

    // Eliminar cliente
    void eliminar(int idCliente);
}