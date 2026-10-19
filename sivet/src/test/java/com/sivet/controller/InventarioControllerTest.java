package com.sivet.controller;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.ProductoDAO;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;
import com.sivet.service.InventarioService;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioControllerTest {

    private InventarioController crearController() {

        MovimientoInventarioDAO movimientoDAO =
                new MovimientoInventarioDAO() {

            private final List<MovimientoInventario>
                    movimientos = new ArrayList<>();

            @Override
            public void guardar(
                    MovimientoInventario movimiento) {

                movimiento.setIdMovimiento(600);
                movimientos.add(movimiento);
            }

            @Override
            public MovimientoInventario buscarPorId(
                    int idMovimiento) {

                for (MovimientoInventario movimiento
                        : movimientos) {

                    if (movimiento.getIdMovimiento()
                            == idMovimiento) {

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
            public List<MovimientoInventario>
            listarPorProducto(int idProducto) {

                List<MovimientoInventario> resultado =
                        new ArrayList<>();

                for (MovimientoInventario movimiento
                        : movimientos) {

                    if (movimiento.getIdProducto()
                            == idProducto) {

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

        ProductoDAO productoDAO =
                new ProductoDAO() {

            @Override
            public void guardar(Producto producto) {
            }

            @Override
            public Producto buscarPorId(int idProducto) {
                return null;
            }

            @Override
            public List<Producto> listar() {
                return new ArrayList<>();
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

        InventarioService service =
                new InventarioService(
                        movimientoDAO,
                        productoDAO);

        return new InventarioController(service);
    }

    @Test
    void debeRegistrarMovimiento() {

        InventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(
                LocalDateTime.now());
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(10);
        movimiento.setSaldo(30);
        movimiento.setMotivo("Compra");
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        boolean resultado =
                controller.registrarMovimiento(
                        movimiento);

        assertTrue(resultado);

        assertEquals(
                600,
                movimiento.getIdMovimiento());
    }

    @Test
    void debeRechazarCantidadCero() {

        InventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(0);
        movimiento.setSaldo(30);
        movimiento.setMotivo("Compra");
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        boolean resultado =
                controller.registrarMovimiento(
                        movimiento);

        assertFalse(resultado);
    }
}