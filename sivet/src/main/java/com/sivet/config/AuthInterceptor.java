package com.sivet.config;

import com.sivet.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ruta = request.getRequestURI();

        /*
         * ==============================
         * RUTAS PÚBLICAS
         * ==============================
         */

        if (ruta.equals("/login")
                || ruta.startsWith("/login/")
                || ruta.equals("/logout")
                || ruta.startsWith("/css/")
                || ruta.startsWith("/js/")
                || ruta.startsWith("/images/")
                || ruta.startsWith("/img/")
                || ruta.startsWith("/static/")) {

            return true;
        }

        /*
         * ==============================
         * OBTENER USUARIO DE LA SESIÓN
         * ==============================
         */

        Usuario usuario =
                (Usuario) request.getSession().getAttribute("usuario");

        /*
         * ==============================
         * SIN SESIÓN
         * ==============================
         */

        if (usuario == null) {

            response.sendRedirect("/login");

            return false;
        }

        int rol = usuario.getIdRol();

        /*
         * ==============================
         * ADMINISTRADOR
         * ==============================
         *
         * El administrador puede acceder
         * a todas las funcionalidades.
         */

        if (rol == 1) {

            return true;
        }

        /*
         * ==============================
         * VENDEDOR
         * ==============================
         *
         * Puede:
         * - Dashboard
         * - Clientes
         * - Ventas
         */

        if (rol == 2) {

            if (esRutaPermitidaVendedor(ruta)) {

                return true;
            }

            return accesoDenegado(response);
        }

        /*
         * ==============================
         * ALMACENERO
         * ==============================
         *
         * Puede:
         * - Dashboard
         * - Productos
         * - Categorías
         * - Compras
         * - Proveedores
         * - Inventario
         */

        if (rol == 3) {

            if (esRutaPermitidaAlmacenero(ruta)) {

                return true;
            }

            return accesoDenegado(response);
        }

        /*
         * ==============================
         * ROL DESCONOCIDO
         * ==============================
         */

        return accesoDenegado(response);
    }


    /*
     * ==========================================
     * RUTAS PERMITIDAS PARA EL VENDEDOR
     * ==========================================
     */

    private boolean esRutaPermitidaVendedor(String ruta) {

        if (ruta.equals("/")) {
            return true;
        }

        if (ruta.equals("/clientes")
                || ruta.startsWith("/clientes/")) {

            return true;
        }

        if (ruta.equals("/ventas")
                || ruta.startsWith("/ventas/")) {

            return true;
        }

        return false;
    }


    /*
     * ==========================================
     * RUTAS PERMITIDAS PARA EL ALMACENERO
     * ==========================================
     */

    private boolean esRutaPermitidaAlmacenero(String ruta) {

        if (ruta.equals("/")) {
            return true;
        }

        if (ruta.equals("/productos")
                || ruta.startsWith("/productos/")) {

            return true;
        }

        if (ruta.equals("/categorias")
                || ruta.startsWith("/categorias/")) {

            return true;
        }

        if (ruta.equals("/compras")
                || ruta.startsWith("/compras/")) {

            return true;
        }

        if (ruta.equals("/proveedores")
                || ruta.startsWith("/proveedores/")) {

            return true;
        }

        if (ruta.equals("/inventario")
                || ruta.startsWith("/inventario/")) {

            return true;
        }

        return false;
    }


    /*
     * ==========================================
     * ACCESO DENEGADO
     * ==========================================
     */

    private boolean accesoDenegado(
            HttpServletResponse response) throws Exception {

        response.sendRedirect("/");

        return false;
    }
}