package com.sivet.dao;

import com.sivet.model.Producto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas TDD para ProductoDAO
public class ProductoDAOTest {

    @Test
    void debeGuardarYBuscarProducto() {

        // Creamos el DAO
        ProductoDAO dao = new ProductoDAOImpl();

        // Creamos un código único
        String codigo =
                "TEST_" + System.currentTimeMillis();

        // Creamos el producto
        Producto producto = new Producto();

        producto.setCodigo(codigo);
        producto.setNombre("Producto de prueba");
        producto.setPrecioCompra(5.00);
        producto.setPrecioVenta(8.00);
        producto.setStockActual(10);
        producto.setStockMinimo(2);
        producto.setEstado(true);

        // Usamos una categoria que ya existe
        producto.setIdCategoria(1);

        // Guardamos
        dao.guardar(producto);

        // Verificamos que PostgreSQL generó ID
        assertTrue(producto.getIdProducto() > 0);

        // Buscamos el producto
        Producto encontrado =
                dao.buscarPorId(producto.getIdProducto());

        // Verificamos que existe
        assertNotNull(encontrado);

        // Verificamos datos
        assertEquals(codigo, encontrado.getCodigo());
        assertEquals(
                "Producto de prueba",
                encontrado.getNombre()
        );

        // Eliminamos el producto de prueba
        dao.eliminar(producto.getIdProducto());
    }
}