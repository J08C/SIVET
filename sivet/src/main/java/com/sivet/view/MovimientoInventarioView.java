package com.sivet.view;

import com.sivet.controller.MovimientoInventarioController;
import com.sivet.model.MovimientoInventario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

// Vista para gestionar movimientos de inventario
public class MovimientoInventarioView {

    private final Scanner scanner;
    private final MovimientoInventarioController movimientoController;

    public MovimientoInventarioView(
            MovimientoInventarioController movimientoController) {

        this.scanner = new Scanner(System.in);
        this.movimientoController = movimientoController;
    }

    public void mostrarMenu() {

        int opcion;

        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("     MOVIMIENTOS DE INVENTARIO");
            System.out.println("=================================");
            System.out.println("1. Registrar movimiento");
            System.out.println("2. Listar movimientos");
            System.out.println("3. Buscar movimiento");
            System.out.println("4. Listar por producto");
            System.out.println("5. Actualizar movimiento");
            System.out.println("6. Eliminar movimiento");
            System.out.println("0. Volver");
            System.out.println("=================================");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            procesarOpcion(opcion);

        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                registrarMovimiento();
                break;

            case 2:
                listarMovimientos();
                break;

            case 3:
                buscarMovimiento();
                break;

            case 4:
                listarPorProducto();
                break;

            case 5:
                actualizarMovimiento();
                break;

            case 6:
                eliminarMovimiento();
                break;

            case 0:
                System.out.println(
                        "Volviendo al menú principal...");
                break;

            default:
                System.out.println(
                        "Opción no válida.");
                break;
        }
    }

    private void registrarMovimiento() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("      REGISTRAR MOVIMIENTO");
        System.out.println("=================================");

        System.out.print(
                "Tipo (ENTRADA/SALIDA): ");

        String tipo = scanner.next();

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        System.out.print("Saldo: ");
        int saldo = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();

        System.out.print("ID del producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setSaldo(saldo);
        movimiento.setMotivo(motivo);
        movimiento.setIdProducto(idProducto);
        movimiento.setIdUsuario(idUsuario);

        boolean resultado =
                movimientoController
                        .registrarMovimiento(movimiento);

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Movimiento registrado correctamente.");

            System.out.println(
                    "ID generado: "
                    + movimiento.getIdMovimiento());

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el movimiento.");
        }
    }

    private void listarMovimientos() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       LISTA DE MOVIMIENTOS");
        System.out.println("=================================");

        List<MovimientoInventario> movimientos =
                movimientoController.listarMovimientos();

        if (movimientos.isEmpty()) {

            System.out.println(
                    "No hay movimientos registrados.");

            return;
        }

        for (MovimientoInventario movimiento
                : movimientos) {

            mostrarDatosMovimiento(movimiento);

            System.out.println(
                    "---------------------------------");
        }
    }

    private void buscarMovimiento() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        BUSCAR MOVIMIENTO");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del movimiento: ");

        int idMovimiento = scanner.nextInt();

        MovimientoInventario movimiento =
                movimientoController
                        .buscarMovimientoPorId(
                                idMovimiento);

        if (movimiento == null) {

            System.out.println(
                    "No se encontró el movimiento.");

            return;
        }

        System.out.println();
        System.out.println("Movimiento encontrado:");

        mostrarDatosMovimiento(movimiento);
    }

    private void listarPorProducto() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("     MOVIMIENTOS POR PRODUCTO");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del producto: ");

        int idProducto = scanner.nextInt();

        List<MovimientoInventario> movimientos =
                movimientoController
                        .listarPorProducto(idProducto);

        if (movimientos.isEmpty()) {

            System.out.println(
                    "No hay movimientos para ese producto.");

            return;
        }

        for (MovimientoInventario movimiento
                : movimientos) {

            mostrarDatosMovimiento(movimiento);

            System.out.println(
                    "---------------------------------");
        }
    }

    private void actualizarMovimiento() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("     ACTUALIZAR MOVIMIENTO");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del movimiento: ");

        int idMovimiento = scanner.nextInt();

        MovimientoInventario movimiento =
                movimientoController
                        .buscarMovimientoPorId(
                                idMovimiento);

        if (movimiento == null) {

            System.out.println(
                    "No se encontró el movimiento.");

            return;
        }

        System.out.println();
        System.out.println("Movimiento actual:");

        mostrarDatosMovimiento(movimiento);

        System.out.println();

        System.out.print(
                "Nuevo tipo (ENTRADA/SALIDA): ");

        String tipo = scanner.next();

        System.out.print("Nueva cantidad: ");
        int cantidad = scanner.nextInt();

        System.out.print("Nuevo saldo: ");
        int saldo = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Nuevo motivo: ");
        String motivo = scanner.nextLine();

        System.out.print("Nuevo ID de producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Nuevo ID de usuario: ");
        int idUsuario = scanner.nextInt();

        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setSaldo(saldo);
        movimiento.setMotivo(motivo);
        movimiento.setIdProducto(idProducto);
        movimiento.setIdUsuario(idUsuario);

        boolean resultado =
                movimientoController
                        .actualizarMovimiento(
                                movimiento);

        if (resultado) {

            System.out.println(
                    "Movimiento actualizado correctamente.");

        } else {

            System.out.println(
                    "No se pudo actualizar el movimiento.");
        }
    }

    private void eliminarMovimiento() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("      ELIMINAR MOVIMIENTO");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del movimiento: ");

        int idMovimiento = scanner.nextInt();

        System.out.print(
                "¿Está seguro? Escriba 1 para confirmar: ");

        int confirmar = scanner.nextInt();

        if (confirmar != 1) {

            System.out.println(
                    "Eliminación cancelada.");

            return;
        }

        boolean resultado =
                movimientoController
                        .eliminarMovimiento(
                                idMovimiento);

        if (resultado) {

            System.out.println(
                    "Movimiento eliminado correctamente.");

        } else {

            System.out.println(
                    "No se pudo eliminar el movimiento.");
        }
    }

    private void mostrarDatosMovimiento(
            MovimientoInventario movimiento) {

        System.out.println(
                "ID movimiento: "
                + movimiento.getIdMovimiento());

        System.out.println(
                "Fecha: "
                + movimiento.getFecha());

        System.out.println(
                "Tipo: "
                + movimiento.getTipo());

        System.out.println(
                "Cantidad: "
                + movimiento.getCantidad());

        System.out.println(
                "Saldo: "
                + movimiento.getSaldo());

        System.out.println(
                "Motivo: "
                + movimiento.getMotivo());

        System.out.println(
                "ID producto: "
                + movimiento.getIdProducto());

        System.out.println(
                "ID usuario: "
                + movimiento.getIdUsuario());
    }
}