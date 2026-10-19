package com.sivet.service;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.ProductoDAO;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioServiceTest {

    private MovimientoInventarioDAO crearMovimientoDAO() {

        return new MovimientoInventarioDAO() {

            private final List<MovimientoInventario> movimientos =
                    new ArrayList<>();

            @Override
            public void guardar(MovimientoInventario movimiento) {
                movimiento.setIdMovimiento(500);
                movimientos.add(movimiento);
            }

            @Override
            public MovimientoInventario buscarPorId(int id) {
                for (MovimientoInventario movimiento : movimientos) {
                    if (movimiento.getIdMovimiento() == id) {
                        return movimiento;
                    }
                }
                return null;
            }

            @Override
            public List<MovimientoInventario> listar() {
                return movimientos;
            }

            @Override
            public List<MovimientoInventario> listarPorProducto(
                    int idProducto) {
                List<MovimientoInventario> resultado =
                        new ArrayList<>();

                for (MovimientoInventario movimiento : movimientos) {
                    if (movimiento.getIdProducto() == idProducto) {
                        resultado.add(movimiento);
                    }
                }

                return resultado;
            }

            @Override
            public void actualizar(
                    MovimientoInventario movimiento) {
            }

            @Override
            public void eliminar(int idMovimiento) {
                movimientos.removeIf(
                        movimiento ->
                                movimiento.getIdMovimiento()
                                        == idMovimiento);
            }
        };
    }

    private ProductoDAO crearProductoDAO() {

        return new ProductoDAO() {

            private Producto producto;

            @Override
            public void guardar(Producto producto) {
                this.producto = producto;
                producto.setIdProducto(1);
            }

            @Override
            public Producto buscarPorId(int idProducto) {
                if (producto != null
                        && producto.getIdProducto() == idProducto) {
                    return producto;
                }

                return null;
            }

            @Override
            public List<Producto> listar() {
                List<Producto> productos =
                        new ArrayList<>();

                if (producto != null) {
                    productos.add(producto);
                }

                return productos;
            }

            @Override
            public boolean actualizar(Producto producto) {
                this.producto = producto;
                return true;
            }

            @Override
            public boolean eliminar(int idProducto) {
                producto = null;
                return true;
            }
        };
    }

    private MovimientoInventario crearMovimiento() {

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(10);
        movimiento.setSaldo(30);
        movimiento.setMotivo("Compra");
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        return movimiento;
    }

    private Producto crearProducto() {

        Producto producto = new Producto();

        producto.setIdProducto(1);
        producto.setCodigo("TEST-001");
        producto.setNombre("Producto Test");
        producto.setPrecioCompra(5);
        producto.setPrecioVenta(8);
        producto.setStockActual(20);
        producto.setStockMinimo(5);
        producto.setEstado(true);
        producto.setIdCategoria(1);

        return producto;
    }

    @Test
    void debeRegistrarMovimiento() {

        InventarioService service =
                new InventarioService(
                        crearMovimientoDAO(),
                        crearProductoDAO());

        MovimientoInventario movimiento =
                crearMovimiento();

        boolean resultado =
                service.registrarMovimiento(movimiento);

        assertTrue(resultado);
        assertEquals(500,
                movimiento.getIdMovimiento());
    }

    @Test
    void debeRechazarMovimientoInvalido() {

        InventarioService service =
                new InventarioService(
                        crearMovimientoDAO(),
                        crearProductoDAO());

        MovimientoInventario movimiento =
                crearMovimiento();

        movimiento.setCantidad(0);

        boolean resultado =
                service.registrarMovimiento(movimiento);

        assertFalse(resultado);
    }

    @Test
    void debeRegistrarEntradaYActualizarStock() {

        MovimientoInventarioDAO movimientoDAO =
                crearMovimientoDAO();

        ProductoDAO productoDAO =
                crearProductoDAO();

        Producto producto =
                crearProducto();

        productoDAO.guardar(producto);

        InventarioService service =
                new InventarioService(
                        movimientoDAO,
                        productoDAO);

        boolean resultado =
                service.registrarEntrada(
                        1,
                        10,
                        1,
                        "Compra de productos");

        assertTrue(resultado);

        Producto actualizado =
                productoDAO.buscarPorId(1);

        assertEquals(
                30,
                actualizado.getStockActual());
    }

    @Test
    void debeRechazarEntradaConCantidadCero() {

        InventarioService service =
                new InventarioService(
                        crearMovimientoDAO(),
                        crearProductoDAO());

        boolean resultado =
                service.registrarEntrada(
                        1,
                        0,
                        1,
                        "Compra");

        assertFalse(resultado);
    }

    @Test
    void debeRegistrarSalidaYActualizarStock() {

        MovimientoInventarioDAO movimientoDAO =
                crearMovimientoDAO();

        ProductoDAO productoDAO =
                crearProductoDAO();

        Producto producto =
                crearProducto();

        productoDAO.guardar(producto);

        InventarioService service =
                new InventarioService(
                        movimientoDAO,
                        productoDAO);

        boolean resultado =
                service.registrarSalida(
                        1,
                        5,
                        1,
                        "Venta de productos");

        assertTrue(resultado);

        Producto actualizado =
                productoDAO.buscarPorId(1);

        assertEquals(
                15,
                actualizado.getStockActual());
    }

    @Test
    void debeRechazarSalidaSiNoHayStock() {

        MovimientoInventarioDAO movimientoDAO =
                crearMovimientoDAO();

        ProductoDAO productoDAO =
                crearProductoDAO();

        Producto producto =
                crearProducto();

        productoDAO.guardar(producto);

        InventarioService service =
                new InventarioService(
                        movimientoDAO,
                        productoDAO);

        boolean resultado =
                service.registrarSalida(
                        1,
                        25,
                        1,
                        "Venta");

        assertFalse(resultado);

        Producto actualizado =
                productoDAO.buscarPorId(1);

        assertEquals(
                20,
                actualizado.getStockActual());
    }
}