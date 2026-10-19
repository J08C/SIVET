package com.sivet.controller;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.model.MovimientoInventario;
import com.sivet.service.MovimientoInventarioService;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovimientoInventarioControllerTest {

    private MovimientoInventarioController crearController() {

        MovimientoInventarioDAO dao =
                new MovimientoInventarioDAO() {

            private final List<MovimientoInventario>
                    movimientos = new ArrayList<>();

            @Override
            public void guardar(
                    MovimientoInventario movimiento) {

                movimiento.setIdMovimiento(400);
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

                for (int i = 0;
                     i < movimientos.size();
                     i++) {

                    if (movimientos.get(i)
                            .getIdMovimiento()
                            == movimiento.getIdMovimiento()) {

                        movimientos.set(i, movimiento);
                        return;
                    }
                }
            }

            @Override
            public void eliminar(
                    int idMovimiento) {

                movimientos.removeIf(
                        movimiento ->
                                movimiento.getIdMovimiento()
                                        == idMovimiento);
            }
        };

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        return new MovimientoInventarioController(
                service);
    }


    private MovimientoInventario crearMovimiento() {

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(
                LocalDateTime.now());

        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(10);
        movimiento.setSaldo(40);
        movimiento.setMotivo(
                "Compra de productos");
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        return movimiento;
    }


    @Test
    void debeRegistrarMovimiento() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        boolean resultado =
                controller.registrarMovimiento(
                        movimiento);

        assertTrue(resultado);

        assertEquals(
                400,
                movimiento.getIdMovimiento());
    }


    @Test
    void debeListarMovimientos() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        controller.registrarMovimiento(
                movimiento);

        List<MovimientoInventario> resultado =
                controller.listarMovimientos();

        assertEquals(
                1,
                resultado.size());
    }


    @Test
    void debeBuscarMovimiento() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        controller.registrarMovimiento(
                movimiento);

        MovimientoInventario encontrado =
                controller.buscarMovimientoPorId(
                        movimiento.getIdMovimiento());

        assertNotNull(encontrado);

        assertEquals(
                "ENTRADA",
                encontrado.getTipo());
    }


    @Test
    void debeListarPorProducto() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        controller.registrarMovimiento(
                movimiento);

        List<MovimientoInventario> resultado =
                controller.listarPorProducto(1);

        assertEquals(
                1,
                resultado.size());
    }


    @Test
    void debeActualizarMovimiento() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        controller.registrarMovimiento(
                movimiento);

        movimiento.setCantidad(20);
        movimiento.setSaldo(50);

        boolean resultado =
                controller.actualizarMovimiento(
                        movimiento);

        assertTrue(resultado);

        assertEquals(
                20,
                movimiento.getCantidad());
    }


    @Test
    void debeEliminarMovimiento() {

        MovimientoInventarioController controller =
                crearController();

        MovimientoInventario movimiento =
                crearMovimiento();

        controller.registrarMovimiento(
                movimiento);

        int id =
                movimiento.getIdMovimiento();

        boolean resultado =
                controller.eliminarMovimiento(id);

        assertTrue(resultado);

        assertNull(
                controller.buscarMovimientoPorId(id));
    }
}