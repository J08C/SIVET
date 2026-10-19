package com.sivet.controller;

import com.sivet.dao.ClienteDAO;
import com.sivet.dao.ClienteDAOImpl;
import com.sivet.model.Cliente;
import com.sivet.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteWebController {

    private final ClienteService clienteService;
    private final ClienteController clienteController;
    private final ClienteDAO clienteDAO;

    public ClienteWebController() {

        this.clienteDAO = new ClienteDAOImpl();

        this.clienteService =
                new ClienteService(clienteDAO);

        this.clienteController =
                new ClienteController(clienteService);
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {

        model.addAttribute(
                "clientes",
                clienteService.listarClientes()
        );

        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute(
                "cliente",
                new Cliente()
        );

        return "cliente-form";
    }

    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable("id") int id,
            Model model) {

        Cliente cliente =
                clienteDAO.buscarPorId(id);

        if (cliente == null) {
            return "redirect:/clientes";
        }

        model.addAttribute(
                "cliente",
                cliente
        );

        return "cliente-form";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(
            Cliente cliente,
            Model model) {

        boolean resultado;

        if (cliente.getIdCliente() > 0) {

            resultado =
                    clienteController.actualizarCliente(
                            cliente
                    );

        } else {

            resultado =
                    clienteController.registrarCliente(
                            cliente
                    );
        }

        if (resultado) {
            return "redirect:/clientes";
        }

        model.addAttribute(
                "error",
                "No se pudo guardar el cliente. "
                + "Verifica los datos ingresados."
        );

        model.addAttribute(
                "cliente",
                cliente
        );

        return "cliente-form";
    }

    @PostMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(
            @PathVariable("id") int id) {

        clienteController.eliminarCliente(id);

        return "redirect:/clientes";
    }
}