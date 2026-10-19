package com.sivet.controller;

import com.sivet.dao.RolDAO;
import com.sivet.dao.RolDAOImpl;
import com.sivet.model.Rol;
import com.sivet.service.RolService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para RolController
public class RolControllerTest {

    @Test
    void debeRegistrarRolCorrectamente() {

        // Creamos el DAO
        RolDAO rolDAO =
                new RolDAOImpl();

        // Creamos el Service
        RolService rolService =
                new RolService(rolDAO);

        // Creamos el Controller
        RolController controller =
                new RolController(rolService);

        // Creamos un rol de prueba
        Rol rol = new Rol();

        // El campo nombre permite máximo 30 caracteres.
        // Usamos un nombre corto y único.
        rol.setNombre(
                "RCT" +
                (System.currentTimeMillis() % 1000000)
        );

        // Registramos el rol
        boolean resultado =
                controller.registrarRol(rol);

        // Debe registrarse correctamente
        assertTrue(resultado);

        // PostgreSQL debe generar un ID
        assertTrue(
                rol.getIdRol() > 0
        );

        // Eliminamos el registro de prueba
        rolDAO.eliminar(
                rol.getIdRol()
        );
    }

    @Test
    void debeRechazarRolSinNombre() {

        // Creamos el DAO
        RolDAO rolDAO =
                new RolDAOImpl();

        // Creamos el Service
        RolService rolService =
                new RolService(rolDAO);

        // Creamos el Controller
        RolController controller =
                new RolController(rolService);

        // Creamos un rol vacío
        Rol rol = new Rol();

        rol.setNombre("");

        // No debe permitir registrar
        boolean resultado =
                controller.registrarRol(rol);

        assertFalse(resultado);
    }
}