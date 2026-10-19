package com.sivet.service;

import com.sivet.dao.ProveedorDAO;
import com.sivet.dao.ProveedorDAOImpl;
import com.sivet.model.Proveedor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para ProveedorService
public class ProveedorServiceTest {

    @Test
    void debeRegistrarProveedorCorrectamente() {

        // Creamos el DAO
        ProveedorDAO proveedorDAO =
                new ProveedorDAOImpl();

        // Creamos el Service
        ProveedorService service =
                new ProveedorService(proveedorDAO);

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
                "Proveedor Test SIVET"
        );

        proveedor.setTelefono("999999999");
        proveedor.setCorreo("proveedortest@sivet.com");
        proveedor.setDireccion("Direccion de prueba");
        proveedor.setEstado(true);

        // Ejecutamos el registro
        boolean resultado =
                service.registrarProveedor(proveedor);

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

        ProveedorService service =
                new ProveedorService(proveedorDAO);

        Proveedor proveedor = new Proveedor();

        proveedor.setRuc("");
        proveedor.setRazonSocial(
                "Proveedor Test"
        );

        // Sin RUC no debe registrarse
        boolean resultado =
                service.registrarProveedor(proveedor);

        assertFalse(resultado);
    }

    @Test
    void debeRechazarProveedorSinRazonSocial() {

        ProveedorDAO proveedorDAO =
                new ProveedorDAOImpl();

        ProveedorService service =
                new ProveedorService(proveedorDAO);

        Proveedor proveedor = new Proveedor();

        proveedor.setRuc("20000000001");
        proveedor.setRazonSocial("");

        // Sin razón social no debe registrarse
        boolean resultado =
                service.registrarProveedor(proveedor);

        assertFalse(resultado);
    }
}