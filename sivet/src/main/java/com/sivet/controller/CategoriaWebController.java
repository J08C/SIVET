package com.sivet.controller;

import com.sivet.dao.CategoriaDAO;
import com.sivet.dao.CategoriaDAOImpl;
import com.sivet.model.Categoria;
import com.sivet.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoriaWebController {

    private final CategoriaService categoriaService;
    private final CategoriaController categoriaController;
    private final CategoriaDAO categoriaDAO;

    public CategoriaWebController() {

        this.categoriaDAO = new CategoriaDAOImpl();

        this.categoriaService =
                new CategoriaService(categoriaDAO);

        this.categoriaController =
                new CategoriaController(categoriaService);
    }

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {

        model.addAttribute(
                "categorias",
                categoriaService.listarCategorias()
        );

        return "categorias";
    }

    @GetMapping("/categorias/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute(
                "categoria",
                new Categoria()
        );

        return "categoria-form";
    }

    @GetMapping("/categorias/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable("id") int id,
            Model model) {

        Categoria categoria =
                categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            return "redirect:/categorias";
        }

        model.addAttribute(
                "categoria",
                categoria
        );

        return "categoria-form";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(
            Categoria categoria,
            Model model) {

        boolean resultado;

        if (categoria.getIdCategoria() > 0) {

            resultado =
                    categoriaController.actualizarCategoria(
                            categoria
                    );

        } else {

            resultado =
                    categoriaController.registrarCategoria(
                            categoria
                    );
        }

        if (resultado) {
            return "redirect:/categorias";
        }

        model.addAttribute(
                "error",
                "No se pudo guardar la categoría. "
                + "Verifica que el nombre sea válido "
                + "y que no esté repetido."
        );

        model.addAttribute(
                "categoria",
                categoria
        );

        return "categoria-form";
    }

    @PostMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(
            @PathVariable("id") int id) {

        categoriaController.eliminarCategoria(id);

        return "redirect:/categorias";
    }
}