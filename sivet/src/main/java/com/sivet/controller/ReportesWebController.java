
package com.sivet.controller;

import com.sivet.dao.CompraDAO;
import com.sivet.dao.CompraDAOImpl;
import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.MovimientoInventarioDAOImpl;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.ProductoDAOImpl;
import com.sivet.dao.VentaDAO;
import com.sivet.dao.VentaDAOImpl;
import com.sivet.model.Compra;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;
import com.sivet.model.Venta;
import com.sivet.service.ReporteExcelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
public class ReportesWebController {

    private final ProductoDAO productoDAO;
    private final VentaDAO ventaDAO;
    private final CompraDAO compraDAO;
    private final MovimientoInventarioDAO movimientoDAO;
    private final ReporteExcelService reporteExcelService;

    public ReportesWebController() {
        this.productoDAO = new ProductoDAOImpl();
        this.ventaDAO = new VentaDAOImpl();
        this.compraDAO = new CompraDAOImpl();
        this.movimientoDAO = new MovimientoInventarioDAOImpl();
        this.reporteExcelService = new ReporteExcelService();
    }

    @GetMapping("/reportes")
    public String mostrarReportes(
            Model model,
            HttpSession session) {

        // Solo el administrador puede ver reportes.
        Object usuarioSesion = session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        com.sivet.model.Usuario usuario =
                (com.sivet.model.Usuario) usuarioSesion;

        if (usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        // Obtener información.
        List<Producto> productos = productoDAO.listar();
        List<Venta> ventas = ventaDAO.listar();
        List<Compra> compras = compraDAO.listar();
        List<MovimientoInventario> movimientos =
                movimientoDAO.listar();

        // Total de ventas.
        double totalVentas = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        // Total de compras.
        double totalCompras = compras.stream()
                .mapToDouble(Compra::getTotal)
                .sum();

        // Productos con stock bajo.
        List<Producto> productosStockBajo = productos.stream()
                .filter(p ->
                        p.getStockActual() <= p.getStockMinimo())
                .toList();

        // Valor aproximado del inventario.
        double valorInventario = productos.stream()
                .mapToDouble(p ->
                        p.getStockActual() * p.getPrecioVenta())
                .sum();

        // Últimos movimientos.
        List<MovimientoInventario> ultimosMovimientos =
                movimientos.stream()
                        .sorted(
                                Comparator.comparing(
                                        MovimientoInventario::getFecha,
                                        Comparator.nullsLast(
                                                Comparator.reverseOrder())))
                        .limit(10)
                        .toList();

        // Enviar datos a Thymeleaf.
        model.addAttribute("productos", productos);
        model.addAttribute("ventas", ventas);
        model.addAttribute("compras", compras);
        model.addAttribute("movimientos", movimientos);

        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("totalCompras", totalCompras);
        model.addAttribute(
                "cantidadStockBajo",
                productosStockBajo.size());
        model.addAttribute("valorInventario", valorInventario);
        model.addAttribute("productosStockBajo", productosStockBajo);
        model.addAttribute("ultimosMovimientos", ultimosMovimientos);

        return "reportes";
    }

    @GetMapping("/reportes/excel")
    public ResponseEntity<byte[]> descargarReporteExcel(
            HttpSession session) throws IOException {

        // Verificar que haya una sesión iniciada.
        Object usuarioSesion = session.getAttribute("usuario");

        if (usuarioSesion == null) {
            return ResponseEntity.status(401).build();
        }

        // Solo el administrador puede descargar el reporte.
        com.sivet.model.Usuario usuario =
                (com.sivet.model.Usuario) usuarioSesion;

        if (usuario.getIdRol() != 1) {
            return ResponseEntity.status(403).build();
        }

        // Obtener las ventas y generar el archivo Excel.
        List<Venta> ventas = ventaDAO.listar();

        byte[] archivo =
                reporteExcelService.generarReporteVentas(ventas);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=reporte_ventas.xlsx")
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(archivo);
    }
}
