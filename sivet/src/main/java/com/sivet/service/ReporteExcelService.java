
package com.sivet.service;

import com.sivet.model.Venta;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteExcelService {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarReporteVentas(List<Venta> ventas)
            throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {

            Sheet hoja = workbook.createSheet("Ventas");

            // =========================
            // ESTILOS DEL REPORTE
            // =========================

            // Encabezado azul oscuro con letras blancas.
            CellStyle estiloEncabezado = workbook.createCellStyle();

            Font fuenteEncabezado = workbook.createFont();
            fuenteEncabezado.setBold(true);
            fuenteEncabezado.setColor(IndexedColors.WHITE.getIndex());
            fuenteEncabezado.setFontHeightInPoints((short) 11);

            estiloEncabezado.setFont(fuenteEncabezado);
            estiloEncabezado.setFillForegroundColor(
                    IndexedColors.DARK_BLUE.getIndex());
            estiloEncabezado.setFillPattern(
                    FillPatternType.SOLID_FOREGROUND);
            estiloEncabezado.setAlignment(HorizontalAlignment.CENTER);
            estiloEncabezado.setVerticalAlignment(
                    VerticalAlignment.CENTER);
            estiloEncabezado.setWrapText(true);

            ponerBordes(estiloEncabezado);

            // Estilo para filas pares.
            CellStyle estiloFilaPar = workbook.createCellStyle();
            estiloFilaPar.setFillForegroundColor(
                    IndexedColors.WHITE.getIndex());
            estiloFilaPar.setFillPattern(
                    FillPatternType.SOLID_FOREGROUND);
            estiloFilaPar.setVerticalAlignment(
                    VerticalAlignment.CENTER);
            ponerBordes(estiloFilaPar);

            // Estilo para filas impares.
            CellStyle estiloFilaImpar = workbook.createCellStyle();
            estiloFilaImpar.setFillForegroundColor(
                    IndexedColors.PALE_BLUE.getIndex());
            estiloFilaImpar.setFillPattern(
                    FillPatternType.SOLID_FOREGROUND);
            estiloFilaImpar.setVerticalAlignment(
                    VerticalAlignment.CENTER);
            ponerBordes(estiloFilaImpar);

            // Estilo para importes monetarios.
            CellStyle estiloMonedaPar =
                    workbook.createCellStyle();
            estiloMonedaPar.cloneStyleFrom(estiloFilaPar);
            estiloMonedaPar.setAlignment(HorizontalAlignment.RIGHT);
            estiloMonedaPar.setDataFormat(
                    workbook.createDataFormat().getFormat(
                            "\"S/ \"#,##0.00"));

            CellStyle estiloMonedaImpar =
                    workbook.createCellStyle();
            estiloMonedaImpar.cloneStyleFrom(estiloFilaImpar);
            estiloMonedaImpar.setAlignment(HorizontalAlignment.RIGHT);
            estiloMonedaImpar.setDataFormat(
                    workbook.createDataFormat().getFormat(
                            "\"S/ \"#,##0.00"));

            // Estilo para identificadores.
            CellStyle estiloIdPar = workbook.createCellStyle();
            estiloIdPar.cloneStyleFrom(estiloFilaPar);
            estiloIdPar.setAlignment(HorizontalAlignment.CENTER);

            CellStyle estiloIdImpar = workbook.createCellStyle();
            estiloIdImpar.cloneStyleFrom(estiloFilaImpar);
            estiloIdImpar.setAlignment(HorizontalAlignment.CENTER);

            // Estilo para fecha.
            CellStyle estiloFechaPar = workbook.createCellStyle();
            estiloFechaPar.cloneStyleFrom(estiloFilaPar);
            estiloFechaPar.setAlignment(HorizontalAlignment.CENTER);

            CellStyle estiloFechaImpar = workbook.createCellStyle();
            estiloFechaImpar.cloneStyleFrom(estiloFilaImpar);
            estiloFechaImpar.setAlignment(HorizontalAlignment.CENTER);

            // =========================
            // ENCABEZADOS
            // =========================

            String[] encabezados = {
                    "ID Venta",
                    "Fecha",
                    "Total (S/)",
                    "ID Usuario",
                    "ID Cliente"
            };

            Row filaEncabezado = hoja.createRow(0);
            filaEncabezado.setHeightInPoints(30);

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
                celda.setCellStyle(estiloEncabezado);
            }

            // =========================
            // DATOS DE LAS VENTAS
            // =========================

            int numeroFila = 1;
            double totalVentas = 0;
            int cantidadVentas = 0;

            if (ventas != null) {
                for (Venta venta : ventas) {

                    if (venta == null) {
                        continue;
                    }

                    Row fila = hoja.createRow(numeroFila);

                    boolean filaImpar = numeroFila % 2 != 0;

                    CellStyle estiloFila =
                            filaImpar ? estiloFilaImpar : estiloFilaPar;

                    CellStyle estiloId =
                            filaImpar ? estiloIdImpar : estiloIdPar;

                    CellStyle estiloFecha =
                            filaImpar ? estiloFechaImpar : estiloFechaPar;

                    CellStyle estiloMoneda =
                            filaImpar ? estiloMonedaImpar : estiloMonedaPar;

                    // ID de venta.
                    Cell celdaIdVenta = fila.createCell(0);
                    celdaIdVenta.setCellValue(venta.getIdVenta());
                    celdaIdVenta.setCellStyle(estiloId);

                    // Fecha de venta.
                    Cell celdaFecha = fila.createCell(1);

                    if (venta.getFecha() != null) {
                        celdaFecha.setCellValue(
                                venta.getFecha().format(FORMATO_FECHA));
                    } else {
                        celdaFecha.setCellValue("Sin fecha");
                    }

                    celdaFecha.setCellStyle(estiloFecha);

                    // Total de la venta.
                    Cell celdaTotal = fila.createCell(2);
                    double total = venta.getTotal();

                    celdaTotal.setCellValue(total);
                    celdaTotal.setCellStyle(estiloMoneda);

                    // ID del usuario.
                    Cell celdaUsuario = fila.createCell(3);
                    celdaUsuario.setCellValue(venta.getIdUsuario());
                    celdaUsuario.setCellStyle(estiloId);

                    // ID del cliente.
                    Cell celdaCliente = fila.createCell(4);

                    if (venta.getIdCliente() != null) {
                        celdaCliente.setCellValue(
                                venta.getIdCliente());
                    } else {
                        celdaCliente.setCellValue("Sin cliente");
                    }

                    celdaCliente.setCellStyle(estiloId);

                    totalVentas += total;
                    cantidadVentas++;
                    numeroFila++;
                }
            }

            // =========================
            // FILA DE TOTALES
            // =========================

            // Se agrega una fila de resumen únicamente si hay ventas.
            if (cantidadVentas > 0) {

                Row filaTotal = hoja.createRow(numeroFila);

                Cell celdaEtiqueta = filaTotal.createCell(0);
                celdaEtiqueta.setCellValue("TOTAL GENERAL");

                CellStyle estiloTotal = workbook.createCellStyle();

                Font fuenteTotal = workbook.createFont();
                fuenteTotal.setBold(true);
                fuenteTotal.setColor(IndexedColors.WHITE.getIndex());

                estiloTotal.setFont(fuenteTotal);
                estiloTotal.setFillForegroundColor(
                        IndexedColors.DARK_BLUE.getIndex());
                estiloTotal.setFillPattern(
                        FillPatternType.SOLID_FOREGROUND);
                estiloTotal.setVerticalAlignment(
                        VerticalAlignment.CENTER);
                ponerBordes(estiloTotal);

                celdaEtiqueta.setCellStyle(estiloTotal);

                // Combinar las primeras dos columnas para la etiqueta.
                hoja.addMergedRegion(
                        new CellRangeAddress(numeroFila, numeroFila, 0, 1));

                Cell celdaTotalGeneral = filaTotal.createCell(2);
                celdaTotalGeneral.setCellValue(totalVentas);

                CellStyle estiloTotalMoneda =
                        workbook.createCellStyle();
                estiloTotalMoneda.cloneStyleFrom(estiloTotal);
                estiloTotalMoneda.setAlignment(
                        HorizontalAlignment.RIGHT);
                estiloTotalMoneda.setDataFormat(
                        workbook.createDataFormat().getFormat(
                                "\"S/ \"#,##0.00"));

                celdaTotalGeneral.setCellStyle(estiloTotalMoneda);

                Cell celdaCantidad = filaTotal.createCell(3);
                celdaCantidad.setCellValue(
                        "Ventas: " + cantidadVentas);
                celdaCantidad.setCellStyle(estiloTotal);

                // La última celda mantiene el estilo del resumen.
                Cell celdaFinal = filaTotal.createCell(4);
                celdaFinal.setCellStyle(estiloTotal);

                filaTotal.setHeightInPoints(25);
            }

            // =========================
            // PRESENTACIÓN DE LA HOJA
            // =========================

            // Ajustar el ancho de las columnas.
            hoja.setColumnWidth(0, 15 * 256);
            hoja.setColumnWidth(1, 23 * 256);
            hoja.setColumnWidth(2, 18 * 256);
            hoja.setColumnWidth(3, 17 * 256);
            hoja.setColumnWidth(4, 17 * 256);

            // Mantener los encabezados visibles al desplazarse.
            hoja.createFreezePane(0, 1);

            // Filtro para buscar y ordenar las ventas.
            hoja.setAutoFilter(
                    new CellRangeAddress(
                            0,
                            Math.max(0, numeroFila - 1),
                            0,
                            encabezados.length - 1));

            // Configuración para impresión.
            hoja.setPrintGridlines(false);
            hoja.setDisplayGridlines(false);

            PrintSetup configuracionImpresion =
                    hoja.getPrintSetup();

            configuracionImpresion.setLandscape(true);
            configuracionImpresion.setFitWidth((short) 1);
            configuracionImpresion.setFitHeight((short) 0);

            hoja.setFitToPage(true);
            hoja.setRepeatingRows(
                    new CellRangeAddress(0, 0, -1, -1));

            // Escribir el archivo Excel en memoria.
            workbook.write(salida);

            return salida.toByteArray();
        }
    }

    /**
     * Aplica bordes discretos a las celdas del reporte.
     */
    private void ponerBordes(CellStyle estilo) {
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);

        estilo.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estilo.setBottomBorderColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estilo.setLeftBorderColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estilo.setRightBorderColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
    }
}
