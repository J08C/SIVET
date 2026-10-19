package com.sivet.service;

import com.sivet.dao.RolDAO;
import com.sivet.dao.RolDAOImpl;
import com.sivet.model.Rol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para RolService
public class RolServiceTest {

    @Test
    void debeRegistrarRolCorrectamente() {

        // Creamos el DAO
        RolDAO rolDAO =
                new RolDAOImpl();

        // Creamos el Service
        RolService service =
                new RolService(rolDAO);

        // Creamos un rol de prueba
        Rol rol = new Rol();

        rol.setNombre(
                "RolTest_"
                        + System.currentTimeMillis()
        );

        // Ejecutamos el registro
        boolean resultado =
                service.registrarRol(rol);

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

        RolDAO rolDAO =
                new RolDAOImpl();

        RolService service =
                new RolService(rolDAO);

        Rol rol = new Rol();

        rol.setNombre("");

        // Sin nombre no debe registrarse
        boolean resultado =
                service.registrarRol(rol);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarRolNulo() {

        RolDAO rolDAO =
                new RolDAOImpl();

        RolService service =
                new RolService(rolDAO);

        // Un rol nulo no es válido
        boolean resultado =
                service.registrarRol(null);

        assertFalse(resultado);
    }
}