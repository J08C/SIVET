package com.sivet.service;

import com.sivet.dao.RolDAO;
import com.sivet.model.Rol;

// Contiene la lógica de negocio relacionada con los roles
public class RolService {

    // Dependemos de la interfaz RolDAO
    private final RolDAO rolDAO;

    // Inyección de dependencia
    public RolService(RolDAO rolDAO) {
        this.rolDAO = rolDAO;
    }

    // Registra un nuevo rol
    public boolean registrarRol(Rol rol) {

        // El rol no puede ser nulo
        if (rol == null) {
            return false;
        }

        // El nombre del rol es obligatorio
        if (rol.getNombre() == null
                || rol.getNombre().trim().isEmpty()) {
            return false;
        }

        // Guardamos el rol
        rolDAO.guardar(rol);

        return true;
    }
}