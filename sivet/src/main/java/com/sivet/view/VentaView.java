package com.sivet.view;

import com.sivet.controller.VentaController;
import com.sivet.model.Venta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

// Vista encargada de gestionar las ventas
public class VentaView {

    private final Scanner scanner;
    private final VentaController ventaController;
    private final DetalleVentaView detalleVentaView;

    public VentaView(
            VentaController ventaController,
            DetalleVentaView detalleVentaView) {

        this.scanner = new Scanner(System.in);
        this.ventaController = ventaController;
        this.detalleVentaView = detalleVentaView;
    }

    // Menú principal de ventas
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          GESTIÓN DE VENTAS");
            System.out.println("=================================");
            System.out.println("1. Registrar venta");
            System.out.println("2. Listar ventas");
            System.out.println("3. Buscar venta");
            System.out.println("4. Actualizar venta");
            System.out.println("5. Eliminar venta");
            System.out.println("6. Verificar stock para venta");
            System.out.println("7. Registrar venta con salida de stock");
            System.out.println("8. Gestionar detalles de venta");
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
                registrarVenta();
                break;

            case 2:
                listarVentas();
                break;

            case 3:
                buscarVenta();
                break;

            case 4:
                actualizarVenta();
                break;

            case 5:
                eliminarVenta();
                break;

            case 6:
                verificarStock();
                break;

            case 7:
                registrarVentaConSalida();
                break;

            case 8:
                detalleVentaView.mostrarMenu();
                break;

            case 0:
                System.out.println(
                        "Volviendo al menú principal..."
                );
                break;

            default:
                System.out.println(
                        "Opción no válida."
                );
                break;
        }
    }

    // Registrar venta
    private void registrarVenta() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          REGISTRAR VENTA");
        System.out.println("=================================");

        System.out.print("Total de la venta: ");
        double total = scanner.nextDouble();

        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print("ID del cliente (0 si no tiene): ");
        int idCliente = scanner.nextInt();

        Venta venta = new Venta();

        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setIdUsuario(idUsuario);

        if (idCliente == 0) {
            venta.setIdCliente(null);
        } else {
            venta.setIdCliente(idCliente);
        }

        boolean resultado =
                ventaController.registrarVenta(
                        venta
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Venta registrada correctamente."
            );

            System.out.println(
                    "ID de venta: "
                            + venta.getIdVenta()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la venta."
            );
        }
    }

    // Listar ventas
    private void listarVentas() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           LISTA DE VENTAS");
        System.out.println("=================================");

        List<Venta> ventas =
                ventaController.listarVentas();

        if (ventas.isEmpty()) {

            System.out.println(
                    "No existen ventas registradas."
            );

            return;
        }

        for (Venta venta : ventas) {

            mostrarDatosVenta(venta);
        }
    }

    // Buscar venta
    private void buscarVenta() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("            BUSCAR VENTA");
        System.out.println("=================================");

        System.out.print("ID de la venta: ");

        int idVenta = scanner.nextInt();

        Venta venta =
                ventaController.buscarVentaPorId(
                        idVenta
                );

        if (venta == null) {

            System.out.println(
                    "No se encontró la venta."
            );

            return;
        }

        mostrarDatosVenta(venta);
    }

    // Actualizar venta
    private void actualizarVenta() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          ACTUALIZAR VENTA");
        System.out.println("=================================");

        System.out.print("ID de la venta: ");
        int idVenta = scanner.nextInt();

        System.out.print("Nuevo total: ");
        double total = scanner.nextDouble();

        System.out.print("Nuevo ID de usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print(
                "Nuevo ID de cliente (0 si no tiene): "
        );

        int idCliente = scanner.nextInt();

        Venta venta = new Venta();

        venta.setIdVenta(idVenta);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setIdUsuario(idUsuario);

        if (idCliente == 0) {
            venta.setIdCliente(null);
        } else {
            venta.setIdCliente(idCliente);
        }

        boolean resultado =
                ventaController.actualizarVenta(
                        venta
                );

        if (resultado) {

            System.out.println(
                    "Venta actualizada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar la venta."
            );
        }
    }

    // Eliminar venta
    private void eliminarVenta() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           ELIMINAR VENTA");
        System.out.println("=================================");

        System.out.print("ID de la venta: ");

        int idVenta = scanner.nextInt();

        boolean resultado =
                ventaController.eliminarVenta(
                        idVenta
                );

        if (resultado) {

            System.out.println(
                    "Venta eliminada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar la venta."
            );
        }
    }

    // Verificar stock
    private void verificarStock() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       VERIFICAR STOCK");
        System.out.println("=================================");

        System.out.print("ID del producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad a vender: ");
        int cantidad = scanner.nextInt();

        boolean resultado =
                ventaController.verificarVenta(
                        idProducto,
                        cantidad
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Hay stock suficiente para realizar la venta."
            );

        } else {

            System.out.println();
            System.out.println(
                    "No hay stock suficiente para realizar la venta."
            );
        }
    }

    // Registrar venta + salida de inventario
    private void registrarVentaConSalida() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("    VENTA + SALIDA DE STOCK");
        System.out.println("=================================");

        System.out.print("Total de la venta: ");
        double total = scanner.nextDouble();

        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print("ID del cliente (0 si no tiene): ");
        int idCliente = scanner.nextInt();

        System.out.print("ID del producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad vendida: ");
        int cantidad = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();

        Venta venta = new Venta();

        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setIdUsuario(idUsuario);

        if (idCliente == 0) {
            venta.setIdCliente(null);
        } else {
            venta.setIdCliente(idCliente);
        }

        boolean resultado =
                ventaController.registrarVentaConSalida(
                        venta,
                        idProducto,
                        cantidad,
                        motivo
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Venta registrada correctamente."
            );

            System.out.println(
                    "ID de venta: "
                            + venta.getIdVenta()
            );

            System.out.println(
                    "Salida de inventario registrada."
            );

            System.out.println(
                    "Stock descontado correctamente."
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la venta con salida de inventario."
            );
        }
    }

    // Mostrar datos de una venta
    private void mostrarDatosVenta(
            Venta venta) {

        System.out.println("---------------------------------");

        System.out.println(
                "ID venta: "
                        + venta.getIdVenta()
        );

        System.out.println(
                "Fecha: "
                        + venta.getFecha()
        );

        System.out.println(
                "Total: "
                        + venta.getTotal()
        );

        System.out.println(
                "ID usuario: "
                        + venta.getIdUsuario()
        );

        System.out.println(
                "ID cliente: "
                        + venta.getIdCliente()
        );

        System.out.println("---------------------------------");
    }
}