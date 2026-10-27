
package com.sivet.service;

import com.sivet.model.Venta;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReporteExcelServiceTest {

    @Test
    void debeGenerarExcelConLosDatosDeLasVentas()
            throws Exception {

        Venta venta = new Venta(
                1,
                LocalDateTime.of(2026, 10, 26, 10, 30),
                25.50,
                2,
                null
        );

        ReporteExcelService servicio =
                new ReporteExcelService();

        byte[] archivo = servicio.generarReporteVentas(
                List.of(venta)
        );

        assertNotNull(archivo);
        assertTrue(archivo.length > 0);

        try (Workbook workbook = WorkbookFactory.create(
                new ByteArrayInputStream(archivo))) {

            var hoja = workbook.getSheet("Ventas");

            assertNotNull(hoja);
            assertEquals("ID Venta",
                    hoja.getRow(0).getCell(0).getStringCellValue());

            assertEquals(1,
                    (int) hoja.getRow(1).getCell(0).getNumericCellValue());

            assertEquals(25.50,
                    hoja.getRow(1).getCell(2).getNumericCellValue(),
                    0.001);

            assertEquals("Sin cliente",
                    hoja.getRow(1).getCell(4).getStringCellValue());
        }
    }
}
