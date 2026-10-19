package com.sivet.view;

import com.sivet.controller.DetalleCompraController;
import com.sivet.model.DetalleCompra;

import java.util.List;
import java.util.Scanner;

// Vista para gestionar los detalles de compra
public class DetalleCompraView {

    private final Scanner scanner;
    private final DetalleCompraController detalleCompraController;

    public DetalleCompraView(
            DetalleCompraController detalleCompraController) {

        this.scanner = new Scanner(System.in);
        this.detalleCompraController = detalleCompraController;
    }

    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("      DETALLES DE COMPRA");
            System.out.println("=================================");
            System.out.println("1. Registrar detalle");
            System.out.println("2. Listar detalles");
            System.out.println("3. Buscar detalle");
            System.out.println("4. Listar por compra");
            System.out.println("5. Actualizar detalle");
            System.out.println("6. Eliminar detalle");
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
                registrarDetalle();
                break;

            case 2:
                listarDetalles();
                break;

            case 3:
                buscarDetalle();
                break;

            case 4:
                listarPorCompra();
                break;

            case 5:
                actualizarDetalle();
                break;

            case 6:
                eliminarDetalle();
                break;

            case 0:
                System.out.println(
                        "Volviendo al menú de compras...");
                break;

            default:
                System.out.println(
                        "Opción no válida.");
                break;
        }
    }

    private void registrarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR DETALLE");
        System.out.println("=================================");

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        System.out.print("Costo unitario: ");
        double costoUnitario = scanner.nextDouble();

        System.out.print("Subtotal: ");
        double subtotal = scanner.nextDouble();

        System.out.print("ID de compra: ");
        int idCompra = scanner.nextInt();

        System.out.print("ID de producto: ");
        int idProducto = scanner.nextInt();

        DetalleCompra detalle =
                new DetalleCompra();

        detalle.setCantidad(cantidad);
        detalle.setCostoUnitario(costoUnitario);
        detalle.setSubtotal(subtotal);
        detalle.setIdCompra(idCompra);
        detalle.setIdProducto(idProducto);

        boolean resultado =
                detalleCompraController
                        .registrarDetalle(detalle);

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Detalle registrado correctamente.");
            System.out.println(
                    "ID generado: "
                    + detalle.getIdDetalleCompra());

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el detalle.");
        }
    }

    private void listarDetalles() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        LISTA DE DETALLES");
        System.out.println("=================================");

        List<DetalleCompra> detalles =
                detalleCompraController.listarDetalles();

        if (detalles.isEmpty()) {

            System.out.println(
                    "No hay detalles registrados.");

            return;
        }

        for (DetalleCompra detalle : detalles) {

            mostrarDatosDetalle(detalle);

            System.out.println(
                    "---------------------------------");
        }
    }

    private void buscarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          BUSCAR DETALLE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del detalle: ");

        int idDetalle =
                scanner.nextInt();

        DetalleCompra detalle =
                detalleCompraController
                        .buscarDetallePorId(
                                idDetalle);

        if (detalle == null) {

            System.out.println(
                    "No se encontró el detalle.");

            return;
        }

        System.out.println();
        System.out.println(
                "Detalle encontrado:");

        mostrarDatosDetalle(detalle);
    }

    private void listarPorCompra() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("      DETALLES DE UNA COMPRA");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID de la compra: ");

        int idCompra =
                scanner.nextInt();

        List<DetalleCompra> detalles =
                detalleCompraController
                        .listarDetallesPorCompra(
                                idCompra);

        if (detalles.isEmpty()) {

            System.out.println(
                    "No hay detalles para esa compra.");

            return;
        }

        for (DetalleCompra detalle : detalles) {

            mostrarDatosDetalle(detalle);

            System.out.println(
                    "---------------------------------");
        }
    }

    private void actualizarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ACTUALIZAR DETALLE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del detalle: ");

        int idDetalle =
                scanner.nextInt();

        DetalleCompra detalle =
                detalleCompraController
                        .buscarDetallePorId(
                                idDetalle);

        if (detalle == null) {

            System.out.println(
                    "No se encontró el detalle.");

            return;
        }

        System.out.println();
        System.out.println(
                "Detalle actual:");

        mostrarDatosDetalle(detalle);

        System.out.println();

        System.out.print("Nueva cantidad: ");
        int cantidad =
                scanner.nextInt();

        System.out.print(
                "Nuevo costo unitario: ");

        double costoUnitario =
                scanner.nextDouble();

        System.out.print("Nuevo subtotal: ");
        double subtotal =
                scanner.nextDouble();

        System.out.print(
                "Nuevo ID de compra: ");

        int idCompra =
                scanner.nextInt();

        System.out.print(
                "Nuevo ID de producto: ");

        int idProducto =
                scanner.nextInt();

        detalle.setCantidad(cantidad);
        detalle.setCostoUnitario(costoUnitario);
        detalle.setSubtotal(subtotal);
        detalle.setIdCompra(idCompra);
        detalle.setIdProducto(idProducto);

        boolean resultado =
                detalleCompraController
                        .actualizarDetalle(
                                detalle);

        if (resultado) {

            System.out.println(
                    "Detalle actualizado correctamente.");

        } else {

            System.out.println(
                    "No se pudo actualizar el detalle.");
        }
    }

    private void eliminarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        ELIMINAR DETALLE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del detalle: ");

        int idDetalle =
                scanner.nextInt();

        System.out.print(
                "¿Está seguro? Escriba 1 para confirmar: ");

        int confirmar =
                scanner.nextInt();

        if (confirmar != 1) {

            System.out.println(
                    "Eliminación cancelada.");

            return;
        }

        boolean resultado =
                detalleCompraController
                        .eliminarDetalle(
                                idDetalle);

        if (resultado) {

            System.out.println(
                    "Detalle eliminado correctamente.");

        } else {

            System.out.println(
                    "No se pudo eliminar el detalle.");
        }
    }

    private void mostrarDatosDetalle(
            DetalleCompra detalle) {

        System.out.println(
                "ID detalle: "
                + detalle.getIdDetalleCompra());

        System.out.println(
                "Cantidad: "
                + detalle.getCantidad());

        System.out.println(
                "Costo unitario: S/ "
                + detalle.getCostoUnitario());

        System.out.println(
                "Subtotal: S/ "
                + detalle.getSubtotal());

        System.out.println(
                "ID compra: "
                + detalle.getIdCompra());

        System.out.println(
                "ID producto: "
                + detalle.getIdProducto());
    }
}