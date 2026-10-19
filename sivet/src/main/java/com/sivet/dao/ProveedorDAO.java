package com.sivet.dao;

import com.sivet.model.Proveedor;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Proveedor
public interface ProveedorDAO {

    // Registrar proveedor
    void guardar(Proveedor proveedor);

    // Buscar proveedor por ID
    Proveedor buscarPorId(int idProveedor);

    // Listar proveedores
    List<Proveedor> listar();

    // Actualizar proveedor
    void actualizar(Proveedor proveedor);

    // Eliminar proveedor
    void eliminar(int idProveedor);
}