package com.sivet.view;

import com.sivet.controller.ProveedorController;
import com.sivet.model.Proveedor;

import java.util.List;
import java.util.Scanner;

// Vista encargada del módulo de proveedores
public class ProveedorView {

    private final Scanner scanner;
    private final ProveedorController proveedorController;

    // Constructor
    public ProveedorView(
            ProveedorController proveedorController) {

        this.scanner = new Scanner(System.in);
        this.proveedorController = proveedorController;
    }

    // Muestra el menú de proveedores
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       GESTIÓN DE PROVEEDORES");
            System.out.println("=================================");
            System.out.println("1. Registrar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Buscar proveedor");
            System.out.println("4. Actualizar proveedor");
            System.out.println("5. Eliminar proveedor");
            System.out.println("0. Volver");
            System.out.println("=================================");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            procesarOpcion(opcion);

        } while (opcion != 0);
    }

    // Procesa la opción seleccionada
    private void procesarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                registrarProveedor();
                break;

            case 2:
                listarProveedores();
                break;

            case 3:
                buscarProveedor();
                break;

            case 4:
                actualizarProveedor();
                break;

            case 5:
                eliminarProveedor();
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

    // Registra un proveedor
    private void registrarProveedor() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR PROVEEDOR");
        System.out.println("=================================");

        scanner.nextLine();

        System.out.print("RUC: ");
        String ruc = scanner.nextLine();

        System.out.print("Razón social: ");
        String razonSocial = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        // Creamos el proveedor
        Proveedor proveedor =
                new Proveedor();

        proveedor.setRuc(ruc);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);
        proveedor.setDireccion(direccion);
        proveedor.setEstado(true);

        // Enviamos al Controller
        boolean resultado =
                proveedorController.registrarProveedor(
                        proveedor
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Proveedor registrado correctamente."
            );

            System.out.println(
                    "ID generado: "
                            + proveedor.getIdProveedor()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el proveedor."
            );
        }
    }

    // Lista todos los proveedores
    private void listarProveedores() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       LISTA DE PROVEEDORES");
        System.out.println("=================================");

        List<Proveedor> proveedores =
                proveedorController.listarProveedores();

        if (proveedores.isEmpty()) {

            System.out.println(
                    "No hay proveedores registrados."
            );

            return;
        }

        for (Proveedor proveedor : proveedores) {

            mostrarDatosProveedor(proveedor);

            System.out.println(
                    "---------------------------------"
            );
        }
    }

    // Busca un proveedor por ID
    private void buscarProveedor() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       BUSCAR PROVEEDOR");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del proveedor: "
        );

        int idProveedor =
                scanner.nextInt();

        Proveedor proveedor =
                proveedorController.buscarProveedorPorId(
                        idProveedor
                );

        if (proveedor == null) {

            System.out.println(
                    "No se encontró el proveedor."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Proveedor encontrado:"
        );

        mostrarDatosProveedor(proveedor);
    }

    // Actualiza un proveedor
    private void actualizarProveedor() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ACTUALIZAR PROVEEDOR");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del proveedor: "
        );

        int idProveedor =
                scanner.nextInt();

        Proveedor proveedor =
                proveedorController.buscarProveedorPorId(
                        idProveedor
                );

        if (proveedor == null) {

            System.out.println(
                    "No se encontró el proveedor."
            );

            return;
        }

        scanner.nextLine();

        System.out.println();
        System.out.println(
                "Proveedor actual:"
        );

        mostrarDatosProveedor(proveedor);

        System.out.println();

        System.out.print("Nuevo RUC: ");
        proveedor.setRuc(
                scanner.nextLine()
        );

        System.out.print("Nueva razón social: ");
        proveedor.setRazonSocial(
                scanner.nextLine()
        );

        System.out.print("Nuevo teléfono: ");
        proveedor.setTelefono(
                scanner.nextLine()
        );

        System.out.print("Nuevo correo: ");
        proveedor.setCorreo(
                scanner.nextLine()
        );

        System.out.print("Nueva dirección: ");
        proveedor.setDireccion(
                scanner.nextLine()
        );

        System.out.print(
                "¿Proveedor activo? 1 = Sí, 0 = No: "
        );

        int estado =
                scanner.nextInt();

        proveedor.setEstado(
                estado == 1
        );

        boolean resultado =
                proveedorController.actualizarProveedor(
                        proveedor
                );

        if (resultado) {

            System.out.println(
                    "Proveedor actualizado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar el proveedor."
            );
        }
    }

    // Elimina un proveedor
    private void eliminarProveedor() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ELIMINAR PROVEEDOR");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del proveedor: "
        );

        int idProveedor =
                scanner.nextInt();

        System.out.print(
                "¿Está seguro? Escriba 1 para confirmar: "
        );

        int confirmar =
                scanner.nextInt();

        if (confirmar != 1) {

            System.out.println(
                    "Eliminación cancelada."
            );

            return;
        }

        boolean resultado =
                proveedorController.eliminarProveedor(
                        idProveedor
                );

        if (resultado) {

            System.out.println(
                    "Proveedor eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar el proveedor."
            );
        }
    }

    // Muestra los datos de un proveedor
    private void mostrarDatosProveedor(
            Proveedor proveedor) {

        System.out.println(
                "ID: "
                        + proveedor.getIdProveedor()
        );

        System.out.println(
                "RUC: "
                        + proveedor.getRuc()
        );

        System.out.println(
                "Razón social: "
                        + proveedor.getRazonSocial()
        );

        System.out.println(
                "Teléfono: "
                        + proveedor.getTelefono()
        );

        System.out.println(
                "Correo: "
                        + proveedor.getCorreo()
        );

        System.out.println(
                "Dirección: "
                        + proveedor.getDireccion()
        );

        System.out.println(
                "Estado: "
                        + (proveedor.isEstado()
                        ? "Activo"
                        : "Inactivo")
        );
    }
}