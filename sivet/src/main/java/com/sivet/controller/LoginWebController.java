package com.sivet.controller;

import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.model.Usuario;
import com.sivet.service.AuthService;
import com.sivet.service.PasswordService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginWebController {

    private final AuthService authService;

    public LoginWebController() {

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        PasswordService passwordService =
                new PasswordService();

        this.authService =
                new AuthService(
                        usuarioDAO,
                        passwordService
                );
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            Model model) {

        Usuario usuarioAutenticado =
                authService.iniciarSesion(
                        usuario,
                        contrasena
                );

        if (usuarioAutenticado == null) {

            model.addAttribute(
                    "error",
                    "Usuario o contraseña incorrectos."
            );

            return "login";
        }

        session.setAttribute(
                "usuario",
                usuarioAutenticado
        );

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String cerrarSesion(
            HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}