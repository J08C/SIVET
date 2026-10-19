package com.sivet.model;

// Clase que representa la tabla PRODUCTO de PostgreSQL
public class Producto {

    // Identificador del producto
    private int idProducto;

    // Código único del producto
    private String codigo;

    // Nombre del producto
    private String nombre;

    // Precio al que se compra el producto
    private double precioCompra;

    // Precio al que se vende el producto
    private double precioVenta;

    // Cantidad actual disponible en almacén
    private int stockActual;

    // Stock mínimo permitido
    private int stockMinimo;

    // Indica si el producto está activo
    private boolean estado;

    // ID de la categoría a la que pertenece
    // Integer permite que inicialmente sea null
    private Integer idCategoria;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los atributos
    public Producto(
            int idProducto,
            String codigo,
            String nombre,
            double precioCompra,
            double precioVenta,
            int stockActual,
            int stockMinimo,
            boolean estado,
            int idCategoria) {

        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
        this.idCategoria = idCategoria;
    }

    // Getter del ID
    public int getIdProducto() {
        return idProducto;
    }

    // Setter del ID
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    // Getter del código
    public String getCodigo() {
        return codigo;
    }

    // Setter del código
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Setter del nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter del precio de compra
    public double getPrecioCompra() {
        return precioCompra;
    }

    // Setter del precio de compra
    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    // Getter del precio de venta
    public double getPrecioVenta() {
        return precioVenta;
    }

    // Setter del precio de venta
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    // Getter del stock actual
    public int getStockActual() {
        return stockActual;
    }

    // Setter del stock actual
    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    // Getter del stock mínimo
    public int getStockMinimo() {
        return stockMinimo;
    }

    // Setter del stock mínimo
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    // Getter del estado
    public boolean isEstado() {
        return estado;
    }

    // Setter del estado
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // Getter del ID de categoría
    // Integer permite devolver null
    public Integer getIdCategoria() {
        return idCategoria;
    }

    // Setter del ID de categoría
    // Integer permite recibir null
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}