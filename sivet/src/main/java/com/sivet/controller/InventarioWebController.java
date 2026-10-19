package com.sivet.controller;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.model.MovimientoInventario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InventarioWebController {

    private final MovimientoInventarioDAO movimientoDAO;

    public InventarioWebController() {

        this.movimientoDAO =
                new MovimientoInventarioDAOImpl();
    }

    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {

        List<MovimientoInventario> movimientos =
                movimientoDAO.listar();

        model.addAttribute(
                "movimientos",
                movimientos
        );

        return "inventario";
    }
}