package com.sivet.view;

import com.sivet.controller.CategoriaController;
import com.sivet.model.Categoria;

import java.util.List;
import java.util.Scanner;

// Vista encargada del módulo de categorías
public class CategoriaView {

    private final Scanner scanner;
    private final CategoriaController categoriaController;

    // Constructor
    public CategoriaView(
            CategoriaController categoriaController) {

        this.scanner = new Scanner(System.in);
        this.categoriaController = categoriaController;
    }

    // Muestra el menú de categorías
    public void mostrarMenu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       GESTIÓN DE CATEGORÍAS");
            System.out.println("=================================");
            System.out.println("1. Registrar categoría");
            System.out.println("2. Listar categorías");
            System.out.println("3. Buscar categoría");
            System.out.println("4. Actualizar categoría");
            System.out.println("5. Eliminar categoría");
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
                registrarCategoria();
                break;

            case 2:
                listarCategorias();
                break;

            case 3:
                buscarCategoria();
                break;

            case 4:
                actualizarCategoria();
                break;

            case 5:
                eliminarCategoria();
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

    // Registra una categoría
    private void registrarCategoria() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       REGISTRAR CATEGORÍA");
        System.out.println("=================================");

        scanner.nextLine();

        System.out.print("Nombre de la categoría: ");

        String nombre =
                scanner.nextLine();

        Categoria categoria =
                new Categoria();

        categoria.setNombre(nombre);

        boolean resultado =
                categoriaController.registrarCategoria(
                        categoria
                );

        if (resultado) {

            System.out.println();
            System.out.println(
                    "Categoría registrada correctamente."
            );

            System.out.println(
                    "ID generado: "
                            + categoria.getIdCategoria()
            );

        } else {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la categoría."
            );
        }
    }

    // Lista todas las categorías
    private void listarCategorias() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       LISTA DE CATEGORÍAS");
        System.out.println("=================================");

        List<Categoria> categorias =
                categoriaController.listarCategorias();

        if (categorias.isEmpty()) {

            System.out.println(
                    "No hay categorías registradas."
            );

            return;
        }

        for (Categoria categoria : categorias) {

            System.out.println(
                    "ID: "
                            + categoria.getIdCategoria()
            );

            System.out.println(
                    "Nombre: "
                            + categoria.getNombre()
            );

            System.out.println(
                    "---------------------------------"
            );
        }
    }

    // Busca una categoría por ID
    private void buscarCategoria() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       BUSCAR CATEGORÍA");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID de la categoría: "
        );

        int idCategoria =
                scanner.nextInt();

        Categoria categoria =
                categoriaController.buscarCategoriaPorId(
                        idCategoria
                );

        if (categoria == null) {

            System.out.println(
                    "No se encontró la categoría."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Categoría encontrada:"
        );

        System.out.println(
                "ID: "
                        + categoria.getIdCategoria()
        );

        System.out.println(
                "Nombre: "
                        + categoria.getNombre()
        );
    }

    // Actualiza una categoría
    private void actualizarCategoria() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ACTUALIZAR CATEGORÍA");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID de la categoría: "
        );

        int idCategoria =
                scanner.nextInt();

        Categoria categoria =
                categoriaController.buscarCategoriaPorId(
                        idCategoria
                );

        if (categoria == null) {

            System.out.println(
                    "No se encontró la categoría."
            );

            return;
        }

        scanner.nextLine();

        System.out.println(
                "Categoría actual: "
                        + categoria.getNombre()
        );

        System.out.print(
                "Nuevo nombre: "
        );

        String nuevoNombre =
                scanner.nextLine();

        categoria.setNombre(nuevoNombre);

        boolean resultado =
                categoriaController.actualizarCategoria(
                        categoria
                );

        if (resultado) {

            System.out.println(
                    "Categoría actualizada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo actualizar la categoría."
            );
        }
    }

    // Elimina una categoría
    private void eliminarCategoria() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       ELIMINAR CATEGORÍA");
        System.out.println("=================================");

        System.out.print(
                "Ingrese ID de la categoría: "
        );

        int idCategoria =
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
                categoriaController.eliminarCategoria(
                        idCategoria
                );

        if (resultado) {

            System.out.println(
                    "Categoría eliminada correctamente."
            );

        } else {

            System.out.println(
                    "No se pudo eliminar la categoría."
            );
        }
    }
}