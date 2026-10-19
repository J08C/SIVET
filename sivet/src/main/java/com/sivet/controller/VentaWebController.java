package com.sivet.controller;

import com.sivet.dao.ClienteDAO;
import com.sivet.dao.ClienteDAOImpl;
import com.sivet.dao.DetalleVentaDAO;
import com.sivet.dao.DetalleVentaDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.dao.VentaDAO;
import com.sivet.dao.VentaDAOImpl;
import com.sivet.model.Cliente;
import com.sivet.model.DetalleVenta;
import com.sivet.model.Producto;
import com.sivet.model.Usuario;
import com.sivet.model.Venta;
import com.sivet.service.VentaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VentaWebController {

    private final VentaService ventaService;
    private final ProductoDAO productoDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;

    public VentaWebController() {

        VentaDAO ventaDAO = new VentaDAOImpl();
        this.productoDAO = new ProductoDAOImpl();
        this.detalleVentaDAO = new DetalleVentaDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();

        this.ventaService = new VentaService(
                ventaDAO,
                productoDAO
        );
    }

    @GetMapping("/ventas")
    public String listarVentas(Model model) {

        model.addAttribute(
                "ventas",
                ventaService.listarVentas()
        );

        return "ventas";
    }

    // Mostrar formulario de nueva venta
    @GetMapping("/ventas/nueva")
    public String mostrarFormularioVenta(Model model) {

        model.addAttribute("productos", productoDAO.listar());
        model.addAttribute("venta", new Venta());

        return "venta-form";
    }

    // Guardar nueva venta usando el usuario de la sesión
    @PostMapping("/ventas/guardar")
    public String guardarVenta(
            @RequestParam("idProducto") int idProducto,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("precioUnitario") double precioUnitario,
            @RequestParam(value = "idCliente", required = false)
                    Integer idCliente,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar que exista una sesión autenticada.
        if (usuario == null) {
            return "redirect:/login";
        }

        // Validaciones básicas.
        if (idProducto <= 0 || cantidad <= 0 || precioUnitario <= 0) {
            model.addAttribute(
                    "error",
                    "Selecciona un producto y valores válidos."
            );
            model.addAttribute("productos", productoDAO.listar());
            model.addAttribute("venta", new Venta());

            return "venta-form";
        }

        Venta venta = new Venta();

        venta.setTotal(
                Math.round(precioUnitario * cantidad * 100.0) / 100.0
        );

        // Usar el ID del usuario que inició sesión.
        venta.setIdUsuario(usuario.getIdUsuario());

        if (idCliente != null && idCliente > 0) {
            venta.setIdCliente(idCliente);
        }

        boolean resultado = ventaService.registrarVentaConSalida(
                venta,
                idProducto,
                cantidad,
                "Venta de producto"
        );

        if (resultado) {
            return "redirect:/ventas";
        }

        model.addAttribute(
                "error",
                "No se pudo registrar la venta. Verifica el stock disponible."
        );
        model.addAttribute("productos", productoDAO.listar());
        model.addAttribute("venta", venta);

        return "venta-form";
    }

    // Ver detalle de una venta
    @GetMapping("/ventas/{id}/detalle")
    public String verDetalleVenta(
            @PathVariable("id") int idVenta,
            Model model) {

        Venta venta = ventaService.buscarVentaPorId(idVenta);

        if (venta == null) {
            return "redirect:/ventas";
        }

        List<DetalleVenta> detalles =
                detalleVentaDAO.listarPorVenta(idVenta);

        Map<Integer, String> nombresProductos = new HashMap<>();
        Map<Integer, String> nombresUsuarios = new HashMap<>();
        Map<Integer, String> nombresClientes = new HashMap<>();

        Usuario usuario = usuarioDAO.buscarPorId(
                venta.getIdUsuario()
        );

        if (usuario != null) {
            nombresUsuarios.put(
                    usuario.getIdUsuario(),
                    usuario.getNombreCompleto()
            );
        }

        if (venta.getIdCliente() != null) {
            Cliente cliente = clienteDAO.buscarPorId(
                    venta.getIdCliente()
            );

            if (cliente != null) {
                nombresClientes.put(
                        cliente.getIdCliente(),
                        cliente.getNombres()
                );
            }
        }

        for (DetalleVenta detalle : detalles) {
            Producto producto = productoDAO.buscarPorId(
                    detalle.getIdProducto()
            );

            if (producto != null) {
                nombresProductos.put(
                        producto.getIdProducto(),
                        producto.getNombre()
                );
            }
        }

        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);
        model.addAttribute("nombresProductos", nombresProductos);
        model.addAttribute("nombresUsuarios", nombresUsuarios);
        model.addAttribute("nombresClientes", nombresClientes);

        return "venta-detalle";
    }
}