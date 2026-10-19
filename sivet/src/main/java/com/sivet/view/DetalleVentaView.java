package com.sivet.view;

import com.sivet.controller.DetalleVentaController;
import com.sivet.model.DetalleVenta;

import java.util.List;
import java.util.Scanner;

// Vista encargada de gestionar los detalles de venta
public class DetalleVentaView {

    private final Scanner scanner;
    private final DetalleVentaController detalleVentaController;

    public DetalleVentaView(
            DetalleVentaController detalleVentaController) {

        this.scanner = new Scanner(System.in);
        this.detalleVentaController = detalleVentaController;
    }

    // Menú principal de detalles de venta
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       DETALLES DE VENTA");
            System.out.println("=================================");
            System.out.println("1. Registrar detalle");
            System.out.println("2. Listar detalles");
            System.out.println("3. Buscar detalle");
            System.out.println("4. Listar por venta");
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
                listarPorVenta();
                break;

            case 5:
                actualizarDetalle();
                break;

            case 6:
                eliminarDetalle();
                break;

            case 0:
                System.out.println(
                        "Volviendo al menú de ventas..."
                );
                break;

            default:
                System.out.println(
                        "Opción no válida."
                );
                break;
        }
    }

    // Registrar detalle
    private void registrarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR DETALLE");
        System.out.println("=================================");

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        System.out.print("Precio unitario: ");
        double precioUnitario = scanner.nextDouble();

        System.out.print("Subtotal: ");
        double subtotal = scanner.nextDouble();

        System.out.print("ID de venta: ");
        int idVenta = scanner.nextInt();

        System.out.print("ID de producto: ");
        int idProducto = scanner.nextInt();

        DetalleVenta detalle =
                new DetalleVenta();

        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(subtotal);
        detalle.setIdVenta(idVenta);
        detalle.setIdProducto(idProducto);

        boolean resultado =
                detalleVentaController.registrarDetalle(
                        detalle
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Detalle de venta registrado correctamente."
            );

            System.out.println(
                    "ID generado: "
                            + detalle.getIdDetalleVenta()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el detalle."
            );
        }
    }

    // Listar detalles
    private void listarDetalles() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       LISTA DE DETALLES");
        System.out.println("=================================");

        List<DetalleVenta> detalles =
                detalleVentaController.listarDetalles();

        if (detalles.isEmpty()) {

            System.out.println(
                    "No existen detalles registrados."
            );

            return;
        }

        for (DetalleVenta detalle : detalles) {
            mostrarDatosDetalle(detalle);
        }
    }

    // Buscar detalle
    private void buscarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        BUSCAR DETALLE");
        System.out.println("=================================");

        System.out.print("ID del detalle: ");

        int idDetalle =
                scanner.nextInt();

        DetalleVenta detalle =
                detalleVentaController.buscarDetallePorId(
                        idDetalle
                );

        if (detalle == null) {

            System.out.println(
                    "No se encontró el detalle."
            );

            return;
        }

        mostrarDatosDetalle(detalle);
    }

    // Listar detalles por venta
    private void listarPorVenta() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       DETALLES DE UNA VENTA");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID de la venta: "
        );

        int idVenta =
                scanner.nextInt();

        List<DetalleVenta> detalles =
                detalleVentaController.listarDetallesPorVenta(
                        idVenta
                );

        if (detalles.isEmpty()) {

            System.out.println(
                    "No existen detalles para esta venta."
            );

            return;
        }

        for (DetalleVenta detalle : detalles) {
            mostrarDatosDetalle(detalle);
        }
    }

    // Actualizar detalle
    private void actualizarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ACTUALIZAR DETALLE");
        System.out.println("=================================");

        System.out.print("ID del detalle: ");
        int idDetalle =
                scanner.nextInt();

        System.out.print("Nueva cantidad: ");
        int cantidad =
                scanner.nextInt();

        System.out.print("Nuevo precio unitario: ");
        double precioUnitario =
                scanner.nextDouble();

        System.out.print("Nuevo subtotal: ");
        double subtotal =
                scanner.nextDouble();

        System.out.print("Nuevo ID de venta: ");
        int idVenta =
                scanner.nextInt();

        System.out.print("Nuevo ID de producto: ");
        int idProducto =
                scanner.nextInt();

        DetalleVenta detalle =
                new DetalleVenta();

        detalle.setIdDetalleVenta(idDetalle);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(subtotal);
        detalle.setIdVenta(idVenta);
        detalle.setIdProducto(idProducto);

        boolean resultado =
                detalleVentaController.actualizarDetalle(
                        detalle
                );

        if (resultado) {

            System.out.println(
                    "Detalle actualizado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar el detalle."
            );
        }
    }

    // Eliminar detalle
    private void eliminarDetalle() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        ELIMINAR DETALLE");
        System.out.println("=================================");

        System.out.print("ID del detalle: ");

        int idDetalle =
                scanner.nextInt();

        boolean resultado =
                detalleVentaController.eliminarDetalle(
                        idDetalle
                );

        if (resultado) {

            System.out.println(
                    "Detalle eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar el detalle."
            );
        }
    }

    // Mostrar datos del detalle
    private void mostrarDatosDetalle(
            DetalleVenta detalle) {

        System.out.println("---------------------------------");

        System.out.println(
                "ID detalle: "
                        + detalle.getIdDetalleVenta()
        );

        System.out.println(
                "Cantidad: "
                        + detalle.getCantidad()
        );

        System.out.println(
                "Precio unitario: "
                        + detalle.getPrecioUnitario()
        );

        System.out.println(
                "Subtotal: "
                        + detalle.getSubtotal()
        );

        System.out.println(
                "ID venta: "
                        + detalle.getIdVenta()
        );

        System.out.println(
                "ID producto: "
                        + detalle.getIdProducto()
        );

        System.out.println("---------------------------------");
    }
}