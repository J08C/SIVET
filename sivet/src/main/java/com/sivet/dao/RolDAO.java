package com.sivet.dao;

import com.sivet.model.Rol;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Rol
public interface RolDAO {

    // Registrar un rol
    void guardar(Rol rol);

    // Buscar un rol por su ID
    Rol buscarPorId(int idRol);

    // Listar todos los roles
    List<Rol> listar();

    // Actualizar un rol
    void actualizar(Rol rol);

    // Eliminar un rol
    void eliminar(int idRol);
}