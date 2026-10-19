package com.sivet.controller;

import com.sivet.dao.ProveedorDAO;
import com.sivet.dao.ProveedorDAOImpl;
import com.sivet.model.Proveedor;
import com.sivet.service.ProveedorService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas para ProveedorController
public class ProveedorControllerTest {

    @Test
    void debeRegistrarProveedorCorrectamente() {

        // Creamos el DAO
        ProveedorDAO proveedorDAO =
                new ProveedorDAOImpl();

        // Creamos el Service
        ProveedorService proveedorService =
                new ProveedorService(proveedorDAO);

        // Creamos el Controller
        ProveedorController controller =
                new ProveedorController(proveedorService);

        // Creamos un proveedor de prueba
        Proveedor proveedor = new Proveedor();

        // RUC de 11 dígitos
        proveedor.setRuc(
                String.valueOf(
                        20000000000L
                                + (System.currentTimeMillis()
                                % 10000000000L)
                )
        );

        proveedor.setRazonSocial(
                "Proveedor Controller Test"
        );

        proveedor.setTelefono("999999999");
        proveedor.setCorreo("controller@sivet.com");
        proveedor.setDireccion("Direccion de prueba");
        proveedor.setEstado(true);

        // Registramos el proveedor
        boolean resultado =
                controller.registrarProveedor(proveedor);

        // Debe registrarse correctamente
        assertTrue(resultado);

        // PostgreSQL debe generar un ID
        assertTrue(
                proveedor.getIdProveedor() > 0
        );

        // Eliminamos el registro de prueba
        proveedorDAO.eliminar(
                proveedor.getIdProveedor()
        );
    }

    @Test
    void debeRechazarProveedorSinRuc() {

        ProveedorDAO proveedorDAO =
                new ProveedorDAOImpl();

        ProveedorService proveedorService =
                new ProveedorService(proveedorDAO);

        ProveedorController controller =
                new ProveedorController(proveedorService);

        Proveedor proveedor = new Proveedor();

        proveedor.setRuc("");
        proveedor.setRazonSocial(
                "Proveedor Test"
        );

        // Sin RUC no debe registrarse
        boolean resultado =
                controller.registrarProveedor(proveedor);

        assertFalse(resultado);
    }
}