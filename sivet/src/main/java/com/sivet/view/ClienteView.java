package com.sivet.view;

import com.sivet.controller.ClienteController;
import com.sivet.model.Cliente;

import java.util.List;
import java.util.Scanner;

// Vista encargada del módulo de clientes
public class ClienteView {

    private final Scanner scanner;
    private final ClienteController clienteController;

    // Constructor
    public ClienteView(
            ClienteController clienteController) {

        this.scanner = new Scanner(System.in);
        this.clienteController = clienteController;
    }

    // Muestra el menú de clientes
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          GESTIÓN DE CLIENTES");
            System.out.println("=================================");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Actualizar cliente");
            System.out.println("5. Eliminar cliente");
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
                registrarCliente();
                break;

            case 2:
                listarClientes();
                break;

            case 3:
                buscarCliente();
                break;

            case 4:
                actualizarCliente();
                break;

            case 5:
                eliminarCliente();
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

    // Registra un cliente
    private void registrarCliente() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          REGISTRAR CLIENTE");
        System.out.println("=================================");

        scanner.nextLine();

        System.out.print("DNI/RUC: ");
        String dniRuc = scanner.nextLine();

        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        // Creamos el objeto Cliente
        Cliente cliente = new Cliente();

        cliente.setDniRuc(dniRuc);
        cliente.setNombres(nombres);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);

        // Enviamos el cliente al Controller
        boolean resultado =
                clienteController.registrarCliente(
                        cliente
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Cliente registrado correctamente."
            );

            System.out.println(
                    "ID generado: "
                            + cliente.getIdCliente()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el cliente."
            );
        }
    }

    // Lista todos los clientes
    private void listarClientes() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          LISTA DE CLIENTES");
        System.out.println("=================================");

        List<Cliente> clientes =
                clienteController.listarClientes();

        if (clientes.isEmpty()) {

            System.out.println(
                    "No hay clientes registrados."
            );

            return;
        }

        for (Cliente cliente : clientes) {

            mostrarDatosCliente(cliente);

            System.out.println(
                    "---------------------------------"
            );
        }
    }

    // Busca un cliente por ID
    private void buscarCliente() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          BUSCAR CLIENTE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del cliente: "
        );

        int idCliente =
                scanner.nextInt();

        Cliente cliente =
                clienteController.buscarClientePorId(
                        idCliente
                );

        if (cliente == null) {

            System.out.println(
                    "No se encontró el cliente."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Cliente encontrado:"
        );

        mostrarDatosCliente(cliente);
    }

    // Actualiza un cliente
    private void actualizarCliente() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          ACTUALIZAR CLIENTE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del cliente: "
        );

        int idCliente =
                scanner.nextInt();

        Cliente cliente =
                clienteController.buscarClientePorId(
                        idCliente
                );

        if (cliente == null) {

            System.out.println(
                    "No se encontró el cliente."
            );

            return;
        }

        scanner.nextLine();

        System.out.println();
        System.out.println(
                "Cliente actual:"
        );

        mostrarDatosCliente(cliente);

        System.out.println();

        System.out.print("Nuevo DNI/RUC: ");
        cliente.setDniRuc(
                scanner.nextLine()
        );

        System.out.print("Nuevos nombres: ");
        cliente.setNombres(
                scanner.nextLine()
        );

        System.out.print("Nuevo teléfono: ");
        cliente.setTelefono(
                scanner.nextLine()
        );

        System.out.print("Nueva dirección: ");
        cliente.setDireccion(
                scanner.nextLine()
        );

        boolean resultado =
                clienteController.actualizarCliente(
                        cliente
                );

        if (resultado) {

            System.out.println(
                    "Cliente actualizado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar el cliente."
            );
        }
    }

    // Elimina un cliente
    private void eliminarCliente() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          ELIMINAR CLIENTE");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del cliente: "
        );

        int idCliente =
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
                clienteController.eliminarCliente(
                        idCliente
                );

        if (resultado) {

            System.out.println(
                    "Cliente eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar el cliente."
            );
        }
    }

    // Muestra los datos de un cliente
    private void mostrarDatosCliente(
            Cliente cliente) {

        System.out.println(
                "ID: "
                        + cliente.getIdCliente()
        );

        System.out.println(
                "DNI/RUC: "
                        + cliente.getDniRuc()
        );

        System.out.println(
                "Nombres: "
                        + cliente.getNombres()
        );

        System.out.println(
                "Teléfono: "
                        + cliente.getTelefono()
        );

        System.out.println(
                "Dirección: "
                        + cliente.getDireccion()
        );
    }
}