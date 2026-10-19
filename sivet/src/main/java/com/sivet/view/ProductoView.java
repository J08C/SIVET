package com.sivet.view;

import com.sivet.controller.ProductoController;
import com.sivet.model.Producto;

import java.util.List;
import java.util.Scanner;

// Vista encargada del módulo de productos
public class ProductoView {

    private final Scanner scanner;
    private final ProductoController productoController;

    // Constructor
    public ProductoView(
            ProductoController productoController) {

        this.scanner = new Scanner(System.in);
        this.productoController = productoController;
    }

    // Muestra el menú de productos
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       GESTIÓN DE PRODUCTOS");
            System.out.println("=================================");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Verificar stock");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
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
                registrarProducto();
                break;

            case 2:
                listarProductos();
                break;

            case 3:
                verificarStock();
                break;

            case 4:
                actualizarProducto();
                break;

            case 5:
                eliminarProducto();
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

    // Registra un producto
    private void registrarProducto() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR PRODUCTO");
        System.out.println("=================================");

        scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio de compra: ");
        double precioCompra = scanner.nextDouble();

        System.out.print("Precio de venta: ");
        double precioVenta = scanner.nextDouble();

        System.out.print("Stock actual: ");
        int stockActual = scanner.nextInt();

        System.out.print("Stock mínimo: ");
        int stockMinimo = scanner.nextInt();

        System.out.print("ID de categoría: ");
        int idCategoria = scanner.nextInt();

        Producto producto = new Producto();

        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVenta(precioVenta);
        producto.setStockActual(stockActual);
        producto.setStockMinimo(stockMinimo);
        producto.setEstado(true);
        producto.setIdCategoria(idCategoria);

        boolean resultado =
                productoController.registrarProducto(
                        producto
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Producto registrado correctamente."
            );

            System.out.println(
                    "ID generado: "
                            + producto.getIdProducto()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar el producto."
            );
        }
    }

    // Lista todos los productos
    private void listarProductos() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       LISTA DE PRODUCTOS");
        System.out.println("=================================");

        List<Producto> productos =
                productoController.listarProductos();

        if (productos.isEmpty()) {

            System.out.println(
                    "No hay productos registrados."
            );

            return;
        }

        for (Producto producto : productos) {

            System.out.println(
                    "ID: "
                            + producto.getIdProducto()
            );

            System.out.println(
                    "Código: "
                            + producto.getCodigo()
            );

            System.out.println(
                    "Nombre: "
                            + producto.getNombre()
            );

            System.out.println(
                    "Precio compra: S/ "
                            + producto.getPrecioCompra()
            );

            System.out.println(
                    "Precio venta: S/ "
                            + producto.getPrecioVenta()
            );

            System.out.println(
                    "Stock actual: "
                            + producto.getStockActual()
            );

            System.out.println(
                    "Stock mínimo: "
                            + producto.getStockMinimo()
            );

            System.out.println(
                    "Categoría: "
                            + producto.getIdCategoria()
            );

            System.out.println(
                    "Estado: "
                            + (producto.isEstado()
                            ? "Activo"
                            : "Inactivo")
            );

            System.out.println(
                    "---------------------------------"
            );
        }
    }

    // Verifica stock
    private void verificarStock() {

        System.out.print(
                "Ingrese el stock disponible: "
        );

        int stockDisponible =
                scanner.nextInt();

        System.out.print(
                "Ingrese la cantidad de venta: "
        );

        int cantidadVenta =
                scanner.nextInt();

        boolean resultado =
                productoController.verificarStock(
                        stockDisponible,
                        cantidadVenta
                );

        if (resultado) {

            System.out.println(
                    "Stock suficiente para realizar la venta."
            );

        } else {

            System.out.println(
                    "Stock insuficiente."
            );
        }
    }

    // Actualiza un producto
    private void actualizarProducto() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ACTUALIZAR PRODUCTO");
        System.out.println("=================================");

        System.out.print("Ingrese ID del producto: ");

        int idProducto =
                scanner.nextInt();

        Producto producto =
                productoController
                        .listarProductos()
                        .stream()
                        .filter(p ->
                                p.getIdProducto()
                                        == idProducto)
                        .findFirst()
                        .orElse(null);

        if (producto == null) {

            System.out.println(
                    "No se encontró el producto."
            );

            return;
        }

        scanner.nextLine();

        System.out.println(
                "Producto encontrado: "
                        + producto.getNombre()
        );

        System.out.print("Nuevo código: ");
        producto.setCodigo(
                scanner.nextLine()
        );

        System.out.print("Nuevo nombre: ");
        producto.setNombre(
                scanner.nextLine()
        );

        System.out.print("Nuevo precio de compra: ");
        producto.setPrecioCompra(
                scanner.nextDouble()
        );

        System.out.print("Nuevo precio de venta: ");
        producto.setPrecioVenta(
                scanner.nextDouble()
        );

        System.out.print("Nuevo stock actual: ");
        producto.setStockActual(
                scanner.nextInt()
        );

        System.out.print("Nuevo stock mínimo: ");
        producto.setStockMinimo(
                scanner.nextInt()
        );

        System.out.print("Nueva categoría: ");
        producto.setIdCategoria(
                scanner.nextInt()
        );

        boolean resultado =
                productoController.actualizarProducto(
                        producto
                );

        if (resultado) {

            System.out.println(
                    "Producto actualizado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar el producto."
            );
        }
    }

    // Elimina un producto
    private void eliminarProducto() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ELIMINAR PRODUCTO");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID del producto: "
        );

        int idProducto =
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
                productoController.eliminarProducto(
                        idProducto
                );

        if (resultado) {

            System.out.println(
                    "Producto eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar el producto."
            );
        }
    }
}