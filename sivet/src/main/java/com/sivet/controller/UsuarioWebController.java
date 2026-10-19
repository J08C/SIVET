package com.sivet.controller;

import com.sivet.dao.RolDAO;
import com.sivet.dao.RolDAOImpl;
import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.model.Rol;
import com.sivet.model.Usuario;
import com.sivet.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsuarioWebController {

    private final UsuarioDAO usuarioDAO;
    private final RolDAO rolDAO;
    private final PasswordService passwordService;

    public UsuarioWebController() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.rolDAO = new RolDAOImpl();
        this.passwordService = new PasswordService();
    }

    // =====================================================
    // LISTAR USUARIOS
    // =====================================================

    @GetMapping("/usuarios")
    public String listarUsuarios(
            Model model,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        // Solo administrador
        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        List<Usuario> usuarios = usuarioDAO.listar();
        List<Rol> roles = rolDAO.listar();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);

        return "usuarios";
    }

    // =====================================================
    // FORMULARIO NUEVO USUARIO
    // =====================================================

    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioNuevo(
            Model model,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAO.listar());

        return "usuario-form";
    }

    // =====================================================
    // GUARDAR USUARIO
    // =====================================================

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(
            @RequestParam("nombreCompleto")
            String nombreCompleto,

            @RequestParam("usuario")
            String nombreUsuario,

            @RequestParam("contrasena")
            String contrasena,

            @RequestParam("idRol")
            int idRol,

            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        if (nombreCompleto == null
                || nombreCompleto.isBlank()
                || nombreUsuario == null
                || nombreUsuario.isBlank()
                || contrasena == null
                || contrasena.isBlank()
                || idRol <= 0) {

            return "redirect:/usuarios/nuevo?error=datos";
        }

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto(
                nombreCompleto.trim());

        usuario.setUsuario(
                nombreUsuario.trim());

        usuario.setContrasenaHash(
                passwordService.generarHash(
                        contrasena));

        usuario.setIdRol(idRol);

        usuarioDAO.guardar(usuario);

        return "redirect:/usuarios";
    }

    // =====================================================
    // EDITAR USUARIO
    // =====================================================

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(
            @PathVariable("id") int id,
            Model model,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        Usuario usuario =
                usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            return "redirect:/usuarios";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolDAO.listar());

        return "usuario-form";
    }

    // =====================================================
    // ACTUALIZAR USUARIO
    // =====================================================

    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(
            @RequestParam("idUsuario")
            int idUsuario,

            @RequestParam("nombreCompleto")
            String nombreCompleto,

            @RequestParam("usuario")
            String nombreUsuario,

            @RequestParam(value = "contrasena",
                    required = false)
            String contrasena,

            @RequestParam("idRol")
            int idRol,

            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        Usuario usuario =
                usuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            return "redirect:/usuarios";
        }

        usuario.setNombreCompleto(
                nombreCompleto.trim());

        usuario.setUsuario(
                nombreUsuario.trim());

        usuario.setIdRol(idRol);

        // Si escribió una contraseña nueva,
        // generar un nuevo hash BCrypt.
        if (contrasena != null
                && !contrasena.isBlank()) {

            usuario.setContrasenaHash(
                    passwordService.generarHash(
                            contrasena));
        }

        usuarioDAO.actualizar(usuario);

        return "redirect:/usuarios";
    }

    // =====================================================
    // ELIMINAR USUARIO
    // =====================================================

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(
            @PathVariable("id") int id,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (usuarioSesion.getIdRol() != 1) {
            return "redirect:/";
        }

        // Evitar que el administrador elimine
        // su propia cuenta.
        if (usuarioSesion.getIdUsuario() == id) {
            return "redirect:/usuarios?error=propio";
        }

        usuarioDAO.eliminar(id);

        return "redirect:/usuarios";
    }
}