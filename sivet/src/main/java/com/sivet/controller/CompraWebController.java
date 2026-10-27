
package com.sivet.controller;

import com.sivet.dao.CompraDAO;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProveedorDAO;
import com.sivet.model.Compra;
import com.sivet.model.Usuario;
import com.sivet.service.CompraService;

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
    private final CompraDAO compraDAO;
    private final ProductoDAO productoDAO;
    private final ProveedorDAO proveedorDAO;

    public CompraWebController(
            CompraService compraService,
            CompraDAO compraDAO,
            ProductoDAO productoDAO,
            ProveedorDAO proveedorDAO) {

        this.compraService = compraService;
        this.compraDAO = compraDAO;
        this.productoDAO = productoDAO;
        this.proveedorDAO = proveedorDAO;
    }

    @GetMapping("/compras")
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.listarCompras());
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

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Al editar, no se registra otra entrada de stock.
        if (compra.getIdCompra() > 0) {
            boolean actualizado = compraService.actualizarCompra(compra);

            if (actualizado) {
                return "redirect:/compras";
            }

            return mostrarError(
                    compra, model, "No se pudo actualizar la compra."
            );
        }

        if (idProducto <= 0) {
            return mostrarError(
                    compra, model, "Selecciona un producto válido."
            );
        }

        if (cantidad <= 0) {
            return mostrarError(
                    compra, model, "La cantidad debe ser mayor que cero."
            );
        }

        if (costoUnitario <= 0
                || Double.isNaN(costoUnitario)
                || Double.isInfinite(costoUnitario)) {
            return mostrarError(
                    compra, model,
                    "El costo unitario debe ser mayor que cero."
            );
        }

        if (compra.getIdProveedor() <= 0) {
            return mostrarError(
                    compra, model, "Selecciona un proveedor válido."
            );
        }

        compra.setIdUsuario(usuario.getIdUsuario());

        double total = Math.round(
                costoUnitario * cantidad * 100.0
        ) / 100.0;

        compra.setTotal(total);

        boolean resultado = compraService.registrarCompraConEntrada(
                compra,
                idProducto,
                cantidad,
                usuario.getIdUsuario(),
                "Compra por mercadería"
        );

        if (resultado) {
            return "redirect:/compras";
        }

        return mostrarError(
                compra,
                model,
                "No se pudo registrar la compra. "
                        + "Verifica los datos y revisa los registros."
        );
    }

    private void cargarListasFormulario(Model model) {
        model.addAttribute("productos", productoDAO.listar());
        model.addAttribute("proveedores", proveedorDAO.listar());
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
    public String eliminarCompra(@PathVariable("id") int id) {
        compraService.eliminarCompra(id);
        return "redirect:/compras";
    }
}
