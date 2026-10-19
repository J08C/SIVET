package com.sivet.view;

import com.sivet.controller.CompraController;
import com.sivet.model.Compra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

// Vista encargada de gestionar las compras
public class CompraView {

    private final Scanner scanner;
    private final CompraController compraController;
    private final DetalleCompraView detalleCompraView;

    public CompraView(
            CompraController compraController,
            DetalleCompraView detalleCompraView) {

        this.scanner = new Scanner(System.in);
        this.compraController = compraController;
        this.detalleCompraView = detalleCompraView;
    }

    // Menú principal de compras
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          GESTIÓN DE COMPRAS");
            System.out.println("=================================");
            System.out.println("1. Registrar compra");
            System.out.println("2. Listar compras");
            System.out.println("3. Buscar compra");
            System.out.println("4. Actualizar compra");
            System.out.println("5. Eliminar compra");
            System.out.println("6. Aumentar stock");
            System.out.println("7. Gestionar detalles de compra");
            System.out.println("8. Registrar compra con entrada de stock");
            System.out.println("0. Volver");
            System.out.println("=================================");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            procesarOpcion(opcion);

        } while (opcion != 0);
    }

    // Procesar opción seleccionada
    private void procesarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                registrarCompra();
                break;

            case 2:
                listarCompras();
                break;

            case 3:
                buscarCompra();
                break;

            case 4:
                actualizarCompra();
                break;

            case 5:
                eliminarCompra();
                break;

            case 6:
                aumentarStock();
                break;

            case 7:
                detalleCompraView.mostrarMenu();
                break;

            case 8:
                registrarCompraConEntrada();
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

    // Registrar compra
    private void registrarCompra() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR COMPRA");
        System.out.println("=================================");

        scanner.nextLine();

        System.out.print("Total de la compra: ");
        double total = scanner.nextDouble();

        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print("ID del proveedor: ");
        int idProveedor = scanner.nextInt();

        Compra compra = new Compra();

        compra.setFecha(LocalDateTime.now());
        compra.setTotal(total);
        compra.setIdUsuario(idUsuario);
        compra.setIdProveedor(idProveedor);

        boolean resultado =
                compraController.registrarCompra(
                        compra
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Compra registrada correctamente."
            );

            System.out.println(
                    "ID de compra: "
                            + compra.getIdCompra()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la compra."
            );
        }
    }

    // Listar compras
    private void listarCompras() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          LISTA DE COMPRAS");
        System.out.println("=================================");

        List<Compra> compras =
                compraController.listarCompras();

        if (compras.isEmpty()) {

            System.out.println(
                    "No existen compras registradas."
            );

            return;
        }

        for (Compra compra : compras) {

            mostrarDatosCompra(compra);
        }
    }

    // Buscar compra
    private void buscarCompra() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          BUSCAR COMPRA");
        System.out.println("=================================");

        System.out.print("ID de la compra: ");

        int idCompra = scanner.nextInt();

        Compra compra =
                compraController.buscarCompraPorId(
                        idCompra
                );

        if (compra == null) {

            System.out.println(
                    "No se encontró la compra."
            );

            return;
        }

        mostrarDatosCompra(compra);
    }

    // Actualizar compra
    private void actualizarCompra() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        ACTUALIZAR COMPRA");
        System.out.println("=================================");

        System.out.print("ID de la compra: ");
        int idCompra = scanner.nextInt();

        System.out.print("Nuevo total: ");
        double total = scanner.nextDouble();

        System.out.print("Nuevo ID de usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print("Nuevo ID de proveedor: ");
        int idProveedor = scanner.nextInt();

        Compra compra = new Compra();

        compra.setIdCompra(idCompra);
        compra.setFecha(LocalDateTime.now());
        compra.setTotal(total);
        compra.setIdUsuario(idUsuario);
        compra.setIdProveedor(idProveedor);

        boolean resultado =
                compraController.actualizarCompra(
                        compra
                );

        if (resultado) {

            System.out.println(
                    "Compra actualizada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar la compra."
            );
        }
    }

    // Eliminar compra
    private void eliminarCompra() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("         ELIMINAR COMPRA");
        System.out.println("=================================");

        System.out.print("ID de la compra: ");

        int idCompra = scanner.nextInt();

        boolean resultado =
                compraController.eliminarCompra(
                        idCompra
                );

        if (resultado) {

            System.out.println(
                    "Compra eliminada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar la compra."
            );
        }
    }

    // Aumentar stock manualmente
    private void aumentarStock() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          AUMENTAR STOCK");
        System.out.println("=================================");

        System.out.print("ID del producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad a agregar: ");
        int cantidad = scanner.nextInt();

        boolean resultado =
                compraController.aumentarStock(
                        idProducto,
                        cantidad
                );

        if (resultado) {

            System.out.println(
                    "Stock aumentado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo aumentar el stock."
            );
        }
    }

    // Registrar compra + entrada de inventario
    private void registrarCompraConEntrada() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("   COMPRA + ENTRADA DE STOCK");
        System.out.println("=================================");

        System.out.print("Total de la compra: ");
        double total = scanner.nextDouble();

        System.out.print("ID del usuario: ");
        int idUsuario = scanner.nextInt();

        System.out.print("ID del proveedor: ");
        int idProveedor = scanner.nextInt();

        System.out.print("ID del producto: ");
        int idProducto = scanner.nextInt();

        System.out.print("Cantidad comprada: ");
        int cantidad = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();

        Compra compra = new Compra();

        compra.setFecha(LocalDateTime.now());
        compra.setTotal(total);
        compra.setIdUsuario(idUsuario);
        compra.setIdProveedor(idProveedor);

        boolean resultado =
                compraController.registrarCompraConEntrada(
                        compra,
                        idProducto,
                        cantidad,
                        motivo
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Compra registrada correctamente."
            );

            System.out.println(
                    "ID de compra: "
                            + compra.getIdCompra()
            );

            System.out.println(
                    "Entrada de inventario registrada."
            );

            System.out.println(
                    "Stock aumentado correctamente."
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la compra "
                            + "con entrada de inventario."
            );
        }
    }

    // Mostrar información de una compra
    private void mostrarDatosCompra(
            Compra compra) {

        System.out.println("---------------------------------");

        System.out.println(
                "ID compra: "
                        + compra.getIdCompra()
        );

        System.out.println(
                "Fecha: "
                        + compra.getFecha()
        );

        System.out.println(
                "Total: "
                        + compra.getTotal()
        );

        System.out.println(
                "ID usuario: "
                        + compra.getIdUsuario()
        );

        System.out.println(
                "ID proveedor: "
                        + compra.getIdProveedor()
        );

        System.out.println("---------------------------------");
    }
}