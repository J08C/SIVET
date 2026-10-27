
package com.sivet.controller;

import com.sivet.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ruta = request.getRequestURI();
        String metodo = request.getMethod();

        // Permitir recursos públicos.
        if (ruta.startsWith("/css/")
                || ruta.startsWith("/js/")
                || ruta.startsWith("/images/")
                || ruta.startsWith("/webjars/")
                || ruta.equals("/favicon.ico")) {
            return true;
        }

        // Permitir el formulario de inicio de sesión.
        if (ruta.equals("/login")) {
            return true;
        }

        // Solo permitir cerrar sesión mediante POST.
        if (ruta.equals("/logout")) {
            if (!"POST".equalsIgnoreCase(metodo)) {
                response.sendError(
                        HttpServletResponse.SC_METHOD_NOT_ALLOWED
                );
                return false;
            }
        }

        HttpSession session = request.getSession(false);

        Usuario usuario = session == null
                ? null
                : (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return false;
        }

        // Solo administradores pueden acceder a configuración.
        if (ruta.equals("/configuracion")
                && usuario.getIdRol() != 1) {
            response.sendRedirect(
                    request.getContextPath() + "/"
            );
            return false;
        }

        return true;
    }
}
