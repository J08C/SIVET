package com.sivet.controller;

import com.sivet.dao.ProductoDAO;
import com.sivet.dao.VentaDAO;
import com.sivet.model.Producto;
import com.sivet.model.Venta;
import com.sivet.service.VentaService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VentaControllerTest {

    @Test
    void debePermitirVentaCuandoHayStock() {

        ProductoDAO productoDAO = new ProductoDAO() {

            @Override
            public Producto buscarPorId(int idProducto) {

                Producto producto = new Producto();

                producto.setIdProducto(1);
                producto.setStockActual(10);

                return producto;
            }

            @Override
            public void guardar(Producto producto) {}

            @Override
            public java.util.List<Producto> listar() {
                return java.util.List.of();
            }

            @Override
            public boolean actualizar(Producto producto) {
                return true;
            }

            @Override
            public boolean eliminar(int idProducto) {
                return true;
            }
        };

        VentaDAO ventaDAO = new VentaDAO() {

            @Override
            public void guardar(Venta venta) {}

            @Override
            public Venta buscarPorId(int idVenta) {
                return null;
            }

            @Override
            public java.util.List<Venta> listar() {
                return java.util.List.of();
            }

            @Override
            public void actualizar(Venta venta) {}

            @Override
            public void eliminar(int idVenta) {}
        };

        VentaService service =
                new VentaService(
                        ventaDAO,
                        productoDAO
                );

        VentaController controller =
                new VentaController(service);

        assertTrue(
                controller.verificarVenta(1, 5)
        );
    }

    @Test
    void debeRechazarVentaCuandoNoHayStock() {

        ProductoDAO productoDAO = new ProductoDAO() {

            @Override
            public Producto buscarPorId(int idProducto) {

                Producto producto = new Producto();

                producto.setIdProducto(1);
                producto.setStockActual(3);

                return producto;
            }

            @Override
            public void guardar(Producto producto) {}

            @Override
            public java.util.List<Producto> listar() {
                return java.util.List.of();
            }

            @Override
            public boolean actualizar(Producto producto) {
                return true;
            }

            @Override
            public boolean eliminar(int idProducto) {
                return true;
            }
        };

        VentaDAO ventaDAO = new VentaDAO() {

            @Override
            public void guardar(Venta venta) {}

            @Override
            public Venta buscarPorId(int idVenta) {
                return null;
            }

            @Override
            public java.util.List<Venta> listar() {
                return java.util.List.of();
            }

            @Override
            public void actualizar(Venta venta) {}

            @Override
            public void eliminar(int idVenta) {}
        };

        VentaService service =
                new VentaService(
                        ventaDAO,
                        productoDAO
                );

        VentaController controller =
                new VentaController(service);

        assertFalse(
                controller.verificarVenta(1, 5)
        );
    }
}