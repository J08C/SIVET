package com.sivet.dao;

import com.sivet.model.Usuario;

import java.util.List;

// Interfaz que define las operaciones de acceso a datos de Usuario
public interface UsuarioDAO {

    // Registrar usuario
    void guardar(Usuario usuario);

    // Buscar usuario por ID
    Usuario buscarPorId(int idUsuario);

    // Buscar usuario por nombre de usuario
    Usuario buscarPorUsuario(String usuario);

    // Listar usuarios
    List<Usuario> listar();

    // Actualizar usuario
    void actualizar(Usuario usuario);

    // Eliminar usuario
    void eliminar(int idUsuario);
}