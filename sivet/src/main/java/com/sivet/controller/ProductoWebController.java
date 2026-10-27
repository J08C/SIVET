
package com.sivet.controller;

import com.sivet.dao.ProductoDAO;
import com.sivet.model.Producto;
import com.sivet.service.ProductoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoWebController {

    private final ProductoService productoService;
    private final ProductoDAO productoDAO;

    public ProductoWebController(
            ProductoService productoService,
            ProductoDAO productoDAO) {

        this.productoService = productoService;
        this.productoDAO = productoDAO;
    }

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable("id") int id,
            Model model) {

        Producto producto = productoDAO.buscarPorId(id);

        if (producto == null) {
            return "redirect:/productos";
        }

        model.addAttribute("producto", producto);
        return "producto-form";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(
            Producto producto,
            Model model) {

        boolean resultado;

        if (producto.getIdProducto() > 0) {
            resultado = productoService.actualizarProducto(producto);
        } else {
            resultado = productoService.registrarProducto(producto);
        }

        if (resultado) {
            return "redirect:/productos";
        }

        model.addAttribute(
                "error",
                "No se pudo guardar el producto. Verifica los datos ingresados."
        );
        model.addAttribute("producto", producto);

        return "producto-form";
    }

    @PostMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") int id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}
