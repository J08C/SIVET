
package com.sivet.controller;

import com.sivet.dao.CompraDAO;
import com.sivet.dao.CompraDAOImpl;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.ProveedorDAO;
import com.sivet.dao.ProveedorDAOImpl;
import com.sivet.model.Compra;
import com.sivet.model.Usuario;
import com.sivet.service.CompraService;
import com.sivet.service.InventarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompraWebController {

    private final CompraService compraService;
    private final CompraController compraController;
    private final CompraDAO compraDAO;
    private final ProductoDAO productoDAO;
    private final ProveedorDAO proveedorDAO;

    public CompraWebController() {
        this.productoDAO = new ProductoDAOImpl();
        this.proveedorDAO = new ProveedorDAOImpl();
        this.compraDAO = new CompraDAOImpl();

        MovimientoInventarioDAOImpl movimientoDAO =
                new MovimientoInventarioDAOImpl();

        InventarioService inventarioService =
                new InventarioService(
                        movimientoDAO,
                        productoDAO
                );

        this.compraService = new CompraService(
                compraDAO,
                productoDAO,
                inventarioService
        );

        this.compraController =
                new CompraController(compraService);
    }

    @GetMapping("/compras")
    public String listarCompras(Model model) {
        model.addAttribute(
                "compras",
                compraService.listarCompras()
        );

        return "compras";
    }

    @GetMapping("/compras/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("compra", new Compra());
        cargarListasFormulario(model);

        return "compra-form";
    }

    @GetMapping("/compras/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable("id") int id,
            Model model) {

        Compra compra = compraDAO.buscarPorId(id);

        if (compra == null) {
            return "redirect:/compras";
        }

        model.addAttribute("compra", compra);
        cargarListasFormulario(model);

        return "compra-form";
    }

    @PostMapping("/compras/guardar")
    public String guardarCompra(
            Compra compra,
            @RequestParam("idProducto") int idProducto,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("costoUnitario") double costoUnitario,
            HttpSession session,
            Model model) {

        // Obtener al usuario autenticado.
        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Al editar no se registra otra entrada de stock.
        if (compra.getIdCompra() > 0) {
            boolean actualizado =
                    compraController.actualizarCompra(compra);

            if (actualizado) {
                return "redirect:/compras";
            }

            return mostrarError(
                    compra,
                    model,
                    "No se pudo actualizar la compra."
            );
        }

        if (idProducto <= 0) {
            return mostrarError(
                    compra,
                    model,
                    "Selecciona un producto válido."
            );
        }

        if (cantidad <= 0) {
            return mostrarError(
                    compra,
                    model,
                    "La cantidad debe ser mayor que cero."
            );
        }

        if (costoUnitario <= 0
                || Double.isNaN(costoUnitario)
                || Double.isInfinite(costoUnitario)) {
            return mostrarError(
                    compra,
                    model,
                    "El costo unitario debe ser mayor que cero."
            );
        }

        if (compra.getIdProveedor() <= 0) {
            return mostrarError(
                    compra,
                    model,
                    "Selecciona un proveedor válido."
            );
        }

        // Asignar el usuario de la sesión.
        compra.setIdUsuario(usuario.getIdUsuario());

        // Calcular el total en el servidor.
        double total = Math.round(
                costoUnitario * cantidad * 100.0
        ) / 100.0;

        compra.setTotal(total);

        System.out.println("========== REGISTRANDO COMPRA ==========");
        System.out.println("Proveedor: " + compra.getIdProveedor());
        System.out.println("Producto: " + idProducto);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Costo unitario: " + costoUnitario);
        System.out.println("Total: " + total);
        System.out.println(
                "Usuario autenticado: " + usuario.getIdUsuario()
        );

        boolean resultado =
                compraService.registrarCompraConEntrada(
                        compra,
                        idProducto,
                        cantidad,
                        usuario.getIdUsuario(),
                        "Compra por mercadería"
                );

        if (resultado) {
            System.out.println("Compra registrada correctamente.");
            return "redirect:/compras";
        }

        return mostrarError(
                compra,
                model,
                "No se pudo registrar la compra. "
                        + "Verifica los datos y revisa la terminal."
        );
    }

    private void cargarListasFormulario(Model model) {
        model.addAttribute(
                "productos",
                productoDAO.listar()
        );

        model.addAttribute(
                "proveedores",
                proveedorDAO.listar()
        );
    }

    private String mostrarError(
            Compra compra,
            Model model,
            String mensaje) {

        model.addAttribute("compra", compra);
        model.addAttribute("error", mensaje);

        cargarListasFormulario(model);

        return "compra-form";
    }

    @PostMapping("/compras/eliminar/{id}")
    public String eliminarCompra(
            @PathVariable("id") int id) {

        compraController.eliminarCompra(id);

        return "redirect:/compras";
    }
}
