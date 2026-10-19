package com.sivet.service;

import com.sivet.dao.ProveedorDAO;
import com.sivet.model.Proveedor;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica de negocio relacionada con los proveedores
public class ProveedorService {

    // Dependemos de la interfaz ProveedorDAO
    private final ProveedorDAO proveedorDAO;

    // Inyección de dependencia
    public ProveedorService(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    // Registra un nuevo proveedor
    public boolean registrarProveedor(
            Proveedor proveedor) {

        // El proveedor no puede ser nulo
        if (proveedor == null) {
            return false;
        }

        // El RUC es obligatorio
        if (proveedor.getRuc() == null
                || proveedor.getRuc().trim().isEmpty()) {
            return false;
        }

        // El RUC debe tener como máximo 11 caracteres
        if (proveedor.getRuc().length() > 11) {
            return false;
        }

        // La razón social es obligatoria
        if (proveedor.getRazonSocial() == null
                || proveedor.getRazonSocial()
                .trim()
                .isEmpty()) {
            return false;
        }

        // Guardamos el proveedor
        proveedorDAO.guardar(proveedor);

        return true;
    }

    // Lista todos los proveedores
    public List<Proveedor> listarProveedores() {

        if (proveedorDAO == null) {
            return new ArrayList<>();
        }

        return proveedorDAO.listar();
    }

    // Busca un proveedor por ID
    public Proveedor buscarProveedorPorId(
            int idProveedor) {

        if (idProveedor <= 0) {
            return null;
        }

        return proveedorDAO.buscarPorId(
                idProveedor
        );
    }

    // Actualiza un proveedor
    public boolean actualizarProveedor(
            Proveedor proveedor) {

        if (proveedor == null) {
            return false;
        }

        if (proveedor.getIdProveedor() <= 0) {
            return false;
        }

        // El RUC es obligatorio
        if (proveedor.getRuc() == null
                || proveedor.getRuc().trim().isEmpty()) {
            return false;
        }

        // El RUC debe tener como máximo 11 caracteres
        if (proveedor.getRuc().length() > 11) {
            return false;
        }

        // La razón social es obligatoria
        if (proveedor.getRazonSocial() == null
                || proveedor.getRazonSocial()
                .trim()
                .isEmpty()) {
            return false;
        }

        // Comprobamos que exista
        Proveedor proveedorExistente =
                proveedorDAO.buscarPorId(
                        proveedor.getIdProveedor()
                );

        if (proveedorExistente == null) {
            return false;
        }

        proveedorDAO.actualizar(proveedor);

        return true;
    }

    // Elimina un proveedor
    public boolean eliminarProveedor(
            int idProveedor) {

        if (idProveedor <= 0) {
            return false;
        }

        // Comprobamos que exista
        Proveedor proveedor =
                proveedorDAO.buscarPorId(
                        idProveedor
                );

        if (proveedor == null) {
            return false;
        }

        proveedorDAO.eliminar(idProveedor);

        return true;
    }
}