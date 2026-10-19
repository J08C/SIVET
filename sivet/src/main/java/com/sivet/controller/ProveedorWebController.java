package com.sivet.controller;

import com.sivet.dao.ProveedorDAO;
import com.sivet.dao.ProveedorDAOImpl;
import com.sivet.model.Proveedor;
import com.sivet.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProveedorWebController {

    private final ProveedorService proveedorService;
    private final ProveedorController proveedorController;
    private final ProveedorDAO proveedorDAO;

    public ProveedorWebController() {

        this.proveedorDAO = new ProveedorDAOImpl();

        this.proveedorService =
                new ProveedorService(proveedorDAO);

        this.proveedorController =
                new ProveedorController(proveedorService);
    }

    @GetMapping("/proveedores")
    public String listarProveedores(Model model) {

        model.addAttribute(
                "proveedores",
                proveedorService.listarProveedores()
        );

        return "proveedores";
    }

    @GetMapping("/proveedores/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        Proveedor proveedor = new Proveedor();

        proveedor.setEstado(true);

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "proveedor-form";
    }

    @GetMapping("/proveedores/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable("id") int id,
            Model model) {

        Proveedor proveedor =
                proveedorDAO.buscarPorId(id);

        if (proveedor == null) {
            return "redirect:/proveedores";
        }

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "proveedor-form";
    }

    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(
            Proveedor proveedor,
            Model model) {

        boolean resultado;

        if (proveedor.getIdProveedor() > 0) {

            resultado =
                    proveedorController.actualizarProveedor(
                            proveedor
                    );

        } else {

            resultado =
                    proveedorController.registrarProveedor(
                            proveedor
                    );
        }

        if (resultado) {
            return "redirect:/proveedores";
        }

        model.addAttribute(
                "error",
                "No se pudo guardar el proveedor. "
                + "Verifica los datos ingresados."
        );

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "proveedor-form";
    }

    @PostMapping("/proveedores/eliminar/{id}")
    public String eliminarProveedor(
            @PathVariable("id") int id) {

        proveedorController.eliminarProveedor(id);

        return "redirect:/proveedores";
    }
}