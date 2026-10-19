package com.sivet.controller;

import com.sivet.dao.CompraDAO;
import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.ProductoDAO;
import com.sivet.model.Compra;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;
import com.sivet.service.CompraService;
import com.sivet.service.InventarioService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompraControllerTest {

    @Test
    void debeRegistrarCompra() {

        CompraDAO compraDAO = new CompraDAO() {

            @Override
            public void guardar(Compra compra) {
                compra.setIdCompra(100);
            }

            @Override
            public Compra buscarPorId(int idCompra) {
                return null;
            }

            @Override
            public java.util.List<Compra> listar() {
                return java.util.List.of();
            }

            @Override
            public void actualizar(Compra compra) {
            }

            @Override
            public void eliminar(int idCompra) {
            }
        };

        CompraService compraService =
                new CompraService(
                        compraDAO,
                        null
                );

        CompraController controller =
                new CompraController(
                        compraService
                );

        Compra compra = new Compra();

        compra.setTotal(50);
        compra.setIdUsuario(1);
        compra.setIdProveedor(1);

        boolean resultado =
                controller.registrarCompra(compra);

        assertTrue(resultado);

        assertEquals(
                100,
                compra.getIdCompra()
        );
    }

    @Test
    void debeRegistrarCompraConEntrada() {

        Producto producto = new Producto();

        producto.setIdProducto(1);
        producto.setStockActual(10);

        ProductoDAO productoDAO =
                new ProductoDAO() {

                    @Override
                    public void guardar(Producto producto) {
                    }

                    @Override
                    public Producto buscarPorId(
                            int idProducto) {

                        return producto;
                    }

                    @Override
                    public java.util.List<Producto> listar() {

                        return java.util.List.of(producto);
                    }

                    @Override
                    public boolean actualizar(
                            Producto producto) {

                        return true;
                    }

                    @Override
                    public boolean eliminar(
                            int idProducto) {

                        return true;
                    }
                };

        MovimientoInventarioDAO movimientoDAO =
                new MovimientoInventarioDAO() {

                    @Override
                    public void guardar(
                            MovimientoInventario movimiento) {
                    }

                    @Override
                    public MovimientoInventario buscarPorId(
                            int idMovimiento) {

                        return null;
                    }

                    @Override
                    public java.util.List<MovimientoInventario>
                    listar() {

                        return java.util.List.of();
                    }

                    @Override
                    public java.util.List<MovimientoInventario>
                    listarPorProducto(
                            int idProducto) {

                        return java.util.List.of();
                    }

                    @Override
                    public void actualizar(
                            MovimientoInventario movimiento) {
                    }

                    @Override
                    public void eliminar(
                            int idMovimiento) {
                    }
                };

        InventarioService inventarioService =
                new InventarioService(
                        movimientoDAO,
                        productoDAO
                );

        CompraDAO compraDAO =
                new CompraDAO() {

                    @Override
                    public void guardar(
                            Compra compra) {

                        compra.setIdCompra(200);
                    }

                    @Override
                    public Compra buscarPorId(
                            int idCompra) {

                        return null;
                    }

                    @Override
                    public java.util.List<Compra> listar() {

                        return java.util.List.of();
                    }

                    @Override
                    public void actualizar(
                            Compra compra) {
                    }

                    @Override
                    public void eliminar(
                            int idCompra) {
                    }
                };

        CompraService compraService =
                new CompraService(
                        compraDAO,
                        productoDAO,
                        inventarioService
                );

        CompraController controller =
                new CompraController(
                        compraService
                );

        Compra compra = new Compra();

        compra.setTotal(100);
        compra.setIdUsuario(1);
        compra.setIdProveedor(1);

        boolean resultado =
                controller.registrarCompraConEntrada(
                        compra,
                        1,
                        5,
                        "1"
                );

        assertTrue(resultado);

        assertEquals(
                200,
                compra.getIdCompra()
        );

        assertEquals(
                15,
                producto.getStockActual()
        );
    }
}