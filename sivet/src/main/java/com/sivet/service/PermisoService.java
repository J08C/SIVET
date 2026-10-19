package com.sivet.service;

import com.sivet.model.Usuario;

/**
 * Servicio encargado de controlar los permisos
 * según el rol del usuario.
 */
public class PermisoService {

    public static final int ADMIN = 1;
    public static final int VENDEDOR = 2;
    public static final int ALMACENERO = 3;

    /**
     * Verifica si el usuario es administrador.
     */
    public boolean esAdministrador(Usuario usuario) {

        return usuario != null
                && usuario.getIdRol() == ADMIN;
    }

    /**
     * Verifica si el usuario es vendedor.
     */
    public boolean esVendedor(Usuario usuario) {

        return usuario != null
                && usuario.getIdRol() == VENDEDOR;
    }

    /**
     * Verifica si el usuario es almacenero.
     */
    public boolean esAlmacenero(Usuario usuario) {

        return usuario != null
                && usuario.getIdRol() == ALMACENERO;
    }

    /**
     * Verifica si puede gestionar productos.
     */
    public boolean puedeGestionarProductos(
            Usuario usuario) {

        return esAdministrador(usuario)
                || esAlmacenero(usuario);
    }

    /**
     * Verifica si puede realizar ventas.
     */
    public boolean puedeRealizarVentas(
            Usuario usuario) {

        return esAdministrador(usuario)
                || esVendedor(usuario);
    }

    /**
     * Verifica si puede gestionar compras.
     */
    public boolean puedeGestionarCompras(
            Usuario usuario) {

        return esAdministrador(usuario)
                || esAlmacenero(usuario);
    }

    /**
     * Verifica si puede gestionar inventario.
     */
    public boolean puedeGestionarInventario(
            Usuario usuario) {

        return esAdministrador(usuario)
                || esAlmacenero(usuario);
    }
}