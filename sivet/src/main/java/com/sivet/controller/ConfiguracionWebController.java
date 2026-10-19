package com.sivet.controller;

import com.sivet.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfiguracionWebController {

    @GetMapping("/configuracion")
    public String mostrarConfiguracion(
            Model model,
            HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        // Verificar sesión
        if (usuario == null) {
            return "redirect:/login";
        }

        // Solo administrador
        if (usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        // Información general del sistema
        model.addAttribute("nombreSistema", "SIVET");
        model.addAttribute(
                "descripcion",
                "Sistema de Inventario y Ventas para una Bodega"
        );
        model.addAttribute(
                "tecnologia",
                "Java + Spring MVC + Thymeleaf"
        );
        model.addAttribute(
                "baseDatos",
                "PostgreSQL"
        );
        model.addAttribute(
                "puerto",
                "8080"
        );
        model.addAttribute(
                "version",
                "1.0"
        );

        return "configuracion";
    }
}