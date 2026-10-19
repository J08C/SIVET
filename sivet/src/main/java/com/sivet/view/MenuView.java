package com.sivet.view;

import com.sivet.controller.CategoriaController;
import com.sivet.controller.ClienteController;
import com.sivet.controller.CompraController;
import com.sivet.controller.DetalleCompraController;
import com.sivet.controller.DetalleVentaController;
import com.sivet.controller.InventarioController;
import com.sivet.controller.MovimientoInventarioController;
import com.sivet.controller.ProductoController;
import com.sivet.controller.ProveedorController;
import com.sivet.controller.VentaController;

import com.sivet.dao.CategoriaDAO;
import com.sivet.dao.CategoriaDAOImpl;
import com.sivet.dao.ClienteDAO;
import com.sivet.dao.ClienteDAOImpl;
import com.sivet.dao.CompraDAO;
import com.sivet.dao.CompraDAOImpl;
import com.sivet.dao.DetalleCompraDAO;
import com.sivet.dao.DetalleCompraDAOImpl;
import com.sivet.dao.DetalleVentaDAO;
import com.sivet.dao.DetalleVentaDAOImpl;
import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.ProveedorDAO;
import com.sivet.dao.ProveedorDAOImpl;
import com.sivet.dao.VentaDAO;
import com.sivet.dao.VentaDAOImpl;

import com.sivet.model.Usuario;

import com.sivet.service.CategoriaService;
import com.sivet.service.ClienteService;
import com.sivet.service.CompraService;
import com.sivet.service.DetalleCompraService;
import com.sivet.service.DetalleVentaService;
import com.sivet.service.InventarioService;
import com.sivet.service.MovimientoInventarioService;
import com.sivet.service.PermisoService;
import com.sivet.service.ProductoService;
import com.sivet.service.ProveedorService;
import com.sivet.service.VentaService;

import java.util.Scanner;

// Vista principal del sistema SIVET
public class MenuView {

    private final Scanner scanner;

    // Usuario que inició sesión
    private final Usuario usuarioAutenticado;

    // Servicio para controlar permisos
    private final PermisoService permisoService;

    public MenuView(Usuario usuarioAutenticado) {

        this.usuarioAutenticado = usuarioAutenticado;
        this.permisoService = new PermisoService();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {

        // ==============================
        // PRODUCTOS
        // ==============================

        ProductoDAO productoDAO =
                new ProductoDAOImpl();

        ProductoService productoService =
                new ProductoService(productoDAO);

        ProductoController productoController =
                new ProductoController(productoService);

        ProductoView productoView =
                new ProductoView(productoController);


        // ==============================
        // CATEGORÍAS
        // ==============================

        CategoriaDAO categoriaDAO =
                new CategoriaDAOImpl();

        CategoriaService categoriaService =
                new CategoriaService(categoriaDAO);

        CategoriaController categoriaController =
                new CategoriaController(categoriaService);

        CategoriaView categoriaView =
                new CategoriaView(categoriaController);


        // ==============================
        // PROVEEDORES
        // ==============================

        ProveedorDAO proveedorDAO =
                new ProveedorDAOImpl();

        ProveedorService proveedorService =
                new ProveedorService(proveedorDAO);

        ProveedorController proveedorController =
                new ProveedorController(proveedorService);

        ProveedorView proveedorView =
                new ProveedorView(proveedorController);


        // ==============================
        // CLIENTES
        // ==============================

        ClienteDAO clienteDAO =
                new ClienteDAOImpl();

        ClienteService clienteService =
                new ClienteService(clienteDAO);

        ClienteController clienteController =
                new ClienteController(clienteService);

        ClienteView clienteView =
                new ClienteView(clienteController);


        // ==============================
        // INVENTARIO
        // ==============================

        MovimientoInventarioDAO movimientoInventarioDAO =
                new MovimientoInventarioDAOImpl();

        MovimientoInventarioService movimientoInventarioService =
                new MovimientoInventarioService(
                        movimientoInventarioDAO
                );

        MovimientoInventarioController movimientoInventarioController =
                new MovimientoInventarioController(
                        movimientoInventarioService
                );

        InventarioService inventarioService =
                new InventarioService(
                        movimientoInventarioDAO,
                        productoDAO
                );

        InventarioController inventarioController =
                new InventarioController(
                        inventarioService
                );

        MovimientoInventarioView movimientoInventarioView =
                new MovimientoInventarioView(
                        movimientoInventarioController
                );


        // ==============================
        // COMPRAS
        // ==============================

        CompraDAO compraDAO =
                new CompraDAOImpl();

        CompraService compraService =
                new CompraService(
                        compraDAO,
                        productoDAO,
                        inventarioService
                );

        CompraController compraController =
                new CompraController(
                        compraService
                );


        // ==============================
        // DETALLES DE COMPRA
        // ==============================

        DetalleCompraDAO detalleCompraDAO =
                new DetalleCompraDAOImpl();

        DetalleCompraService detalleCompraService =
                new DetalleCompraService(
                        detalleCompraDAO
                );

        DetalleCompraController detalleCompraController =
                new DetalleCompraController(
                        detalleCompraService
                );

        DetalleCompraView detalleCompraView =
                new DetalleCompraView(
                        detalleCompraController
                );


        // ==============================
        // VISTA DE COMPRAS
        // ==============================

        CompraView compraView =
                new CompraView(
                        compraController,
                        detalleCompraView
                );


        // ==============================
        // VENTAS
        // ==============================

        VentaDAO ventaDAO =
                new VentaDAOImpl();

        VentaService ventaService =
                new VentaService(
                        ventaDAO,
                        productoDAO,
                        inventarioService
                );

        VentaController ventaController =
                new VentaController(
                        ventaService
                );


        // ==============================
        // DETALLES DE VENTA
        // ==============================

        DetalleVentaDAO detalleVentaDAO =
                new DetalleVentaDAOImpl();

        DetalleVentaService detalleVentaService =
                new DetalleVentaService(
                        detalleVentaDAO
                );

        DetalleVentaController detalleVentaController =
                new DetalleVentaController(
                        detalleVentaService
                );

        DetalleVentaView detalleVentaView =
                new DetalleVentaView(
                        detalleVentaController
                );


        // ==============================
        // VISTA DE VENTAS
        // ==============================

        VentaView ventaView =
                new VentaView(
                        ventaController,
                        detalleVentaView
                );


        // ==============================
        // MENÚ PRINCIPAL
        // ==============================

        int opcion;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("             SISTEMA SIVET");
            System.out.println("======================================");

            System.out.println(
                    "Usuario: "
                            + usuarioAutenticado.getNombreCompleto()
            );

            System.out.println(
                    "Rol ID: "
                            + usuarioAutenticado.getIdRol()
            );

            System.out.println("======================================");

            // PRODUCTOS
            if (permisoService.puedeGestionarProductos(
                    usuarioAutenticado)) {

                System.out.println(
                        "1. Gestión de Productos"
                );
            }

            // CATEGORÍAS
            if (permisoService.puedeGestionarProductos(
                    usuarioAutenticado)) {

                System.out.println(
                        "2. Gestión de Categorías"
                );
            }

            // PROVEEDORES
            if (permisoService.puedeGestionarCompras(
                    usuarioAutenticado)) {

                System.out.println(
                        "3. Gestión de Proveedores"
                );
            }

            // CLIENTES
            if (permisoService.puedeRealizarVentas(
                    usuarioAutenticado)) {

                System.out.println(
                        "4. Gestión de Clientes"
                );
            }

            // COMPRAS
            if (permisoService.puedeGestionarCompras(
                    usuarioAutenticado)) {

                System.out.println(
                        "5. Gestión de Compras"
                );
            }

            // INVENTARIO
            if (permisoService.puedeGestionarInventario(
                    usuarioAutenticado)) {

                System.out.println(
                        "6. Gestión de Inventario"
                );
            }

            // VENTAS
            if (permisoService.puedeRealizarVentas(
                    usuarioAutenticado)) {

                System.out.println(
                        "7. Gestión de Ventas"
                );
            }

            System.out.println("0. Salir");

            System.out.println("======================================");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:

                    if (permisoService.puedeGestionarProductos(
                            usuarioAutenticado)) {

                        productoView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar productos."
                        );
                    }

                    break;


                case 2:

                    if (permisoService.puedeGestionarProductos(
                            usuarioAutenticado)) {

                        categoriaView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar categorías."
                        );
                    }

                    break;


                case 3:

                    if (permisoService.puedeGestionarCompras(
                            usuarioAutenticado)) {

                        proveedorView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar proveedores."
                        );
                    }

                    break;


                case 4:

                    if (permisoService.puedeRealizarVentas(
                            usuarioAutenticado)) {

                        clienteView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar clientes."
                        );
                    }

                    break;


                case 5:

                    if (permisoService.puedeGestionarCompras(
                            usuarioAutenticado)) {

                        compraView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar compras."
                        );
                    }

                    break;


                case 6:

                    if (permisoService.puedeGestionarInventario(
                            usuarioAutenticado)) {

                        movimientoInventarioView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar inventario."
                        );
                    }

                    break;


                case 7:

                    if (permisoService.puedeRealizarVentas(
                            usuarioAutenticado)) {

                        ventaView.mostrarMenu();

                    } else {

                        System.out.println(
                                "No tiene permisos para gestionar ventas."
                        );
                    }

                    break;


                case 0:

                    System.out.println();
                    System.out.println(
                            "Saliendo del sistema SIVET..."
                    );

                    break;


                default:

                    System.out.println();
                    System.out.println(
                            "Opción no válida."
                    );

                    break;
            }

        } while (opcion != 0);
    }
}