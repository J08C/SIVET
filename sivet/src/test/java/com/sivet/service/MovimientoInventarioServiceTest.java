package com.sivet.service;

import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.model.MovimientoInventario;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovimientoInventarioServiceTest {

    private MovimientoInventarioDAO crearDAO() {

        return new MovimientoInventarioDAO() {

            private final List<MovimientoInventario> movimientos =
                    new ArrayList<>();

            @Override
            public void guardar(
                    MovimientoInventario movimiento) {

                movimiento.setIdMovimiento(300);
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
            public void eliminar(int idMovimiento) {

                movimientos.removeIf(
                        movimiento ->
                                movimiento.getIdMovimiento()
                                        == idMovimiento);
            }
        };
    }


    private MovimientoInventario crearMovimiento() {

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(
                LocalDateTime.now());

        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(10);
        movimiento.setSaldo(40);
        movimiento.setMotivo("Compra de productos");
        movimiento.setIdProducto(1);
        movimiento.setIdUsuario(1);

        return movimiento;
    }


    @Test
    void debeRegistrarMovimiento() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        boolean resultado =
                service.registrarMovimiento(
                        movimiento);

        assertTrue(resultado);

        assertEquals(
                300,
                movimiento.getIdMovimiento());
    }


    @Test
    void debeRechazarCantidadCero() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        movimiento.setCantidad(0);

        boolean resultado =
                service.registrarMovimiento(
                        movimiento);

        assertFalse(resultado);
    }


    @Test
    void debeRechazarTipoInvalido() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        movimiento.setTipo("OTRO");

        boolean resultado =
                service.registrarMovimiento(
                        movimiento);

        assertFalse(resultado);
    }


    @Test
    void debeBuscarMovimientoPorId() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        service.registrarMovimiento(
                movimiento);

        MovimientoInventario encontrado =
                service.buscarMovimientoPorId(
                        movimiento.getIdMovimiento());

        assertNotNull(encontrado);

        assertEquals(
                "ENTRADA",
                encontrado.getTipo());

        assertEquals(
                10,
                encontrado.getCantidad());
    }


    @Test
    void debeListarMovimientosPorProducto() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento1 =
                crearMovimiento();

        MovimientoInventario movimiento2 =
                crearMovimiento();

        movimiento2.setIdProducto(2);

        service.registrarMovimiento(movimiento1);
        service.registrarMovimiento(movimiento2);

        List<MovimientoInventario> resultado =
                service.listarPorProducto(1);

        assertEquals(
                1,
                resultado.size());
    }


    @Test
    void debeActualizarMovimiento() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        service.registrarMovimiento(
                movimiento);

        movimiento.setCantidad(20);
        movimiento.setSaldo(50);

        boolean resultado =
                service.actualizarMovimiento(
                        movimiento);

        assertTrue(resultado);

        assertEquals(
                20,
                movimiento.getCantidad());

        assertEquals(
                50,
                movimiento.getSaldo());
    }


    @Test
    void debeEliminarMovimiento() {

        MovimientoInventarioDAO dao =
                crearDAO();

        MovimientoInventarioService service =
                new MovimientoInventarioService(dao);

        MovimientoInventario movimiento =
                crearMovimiento();

        service.registrarMovimiento(
                movimiento);

        int id =
                movimiento.getIdMovimiento();

        boolean resultado =
                service.eliminarMovimiento(id);

        assertTrue(resultado);

        assertNull(
                service.buscarMovimientoPorId(id));
    }
}