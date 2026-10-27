
package com.sivet.controller;

import com.sivet.dao.CompraDAO;
import com.sivet.dao.MovimientoInventarioDAO;
import com.sivet.dao.ProductoDAO;
import com.sivet.dao.VentaDAO;
import com.sivet.model.Compra;
import com.sivet.model.MovimientoInventario;
import com.sivet.model.Producto;
import com.sivet.model.Usuario;
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

    public ReportesWebController(
            ProductoDAO productoDAO,
            VentaDAO ventaDAO,
            CompraDAO compraDAO,
            MovimientoInventarioDAO movimientoDAO,
            ReporteExcelService reporteExcelService) {

        this.productoDAO = productoDAO;
        this.ventaDAO = ventaDAO;
        this.compraDAO = compraDAO;
        this.movimientoDAO = movimientoDAO;
        this.reporteExcelService = reporteExcelService;
    }

    @GetMapping("/reportes")
    public String mostrarReportes(
            Model model,
            HttpSession session) {

        Object usuarioSesion = session.getAttribute("usuario");

        if (!(usuarioSesion instanceof Usuario usuario)) {
            return "redirect:/login";
        }

        // Solo el administrador puede ver los reportes.
        if (usuario.getIdRol() != 1) {
            return "redirect:/";
        }

        List<Producto> productos = productoDAO.listar();
        List<Venta> ventas = ventaDAO.listar();
        List<Compra> compras = compraDAO.listar();
        List<MovimientoInventario> movimientos = movimientoDAO.listar();

        double totalVentas = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        double totalCompras = compras.stream()
                .mapToDouble(Compra::getTotal)
                .sum();

        List<Producto> productosStockBajo = productos.stream()
                .filter(p -> p.getStockActual() <= p.getStockMinimo())
                .toList();

        double valorInventario = productos.stream()
                .mapToDouble(p ->
                        p.getStockActual() * p.getPrecioVenta())
                .sum();

        List<MovimientoInventario> ultimosMovimientos =
                movimientos.stream()
                        .sorted(
                                Comparator.comparing(
                                        MovimientoInventario::getFecha,
                                        Comparator.nullsLast(
                                                Comparator.reverseOrder())
                                )
                        )
                        .limit(10)
                        .toList();

        model.addAttribute("productos", productos);
        model.addAttribute("ventas", ventas);
        model.addAttribute("compras", compras);
        model.addAttribute("movimientos", movimientos);

        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("totalCompras", totalCompras);
        model.addAttribute("cantidadStockBajo", productosStockBajo.size());
        model.addAttribute("valorInventario", valorInventario);
        model.addAttribute("productosStockBajo", productosStockBajo);
        model.addAttribute("ultimosMovimientos", ultimosMovimientos);

        return "reportes";
    }

    @GetMapping("/reportes/excel")
    public ResponseEntity<byte[]> descargarReporteExcel(
            HttpSession session) throws IOException {

        Object usuarioSesion = session.getAttribute("usuario");

        if (!(usuarioSesion instanceof Usuario usuario)) {
            return ResponseEntity.status(401).build();
        }

        // Solo el administrador puede descargar el reporte.
        if (usuario.getIdRol() != 1) {
            return ResponseEntity.status(403).build();
        }

        List<Venta> ventas = ventaDAO.listar();

        byte[] archivo =
                reporteExcelService.generarReporteVentas(ventas);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=reporte_ventas.xlsx"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(archivo);
    }
}
