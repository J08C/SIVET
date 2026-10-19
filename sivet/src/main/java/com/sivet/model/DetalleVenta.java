package com.sivet.model;

// Clase que representa la tabla DETALLE_VENTA de PostgreSQL
public class DetalleVenta {

    // Identificador del detalle
    private int idDetalleVenta;

    // Cantidad de productos vendidos
    private int cantidad;

    // Precio unitario del producto
    private double precioUnitario;

    // Subtotal del detalle
    private double subtotal;

    // ID de la venta
    private int idVenta;

    // ID del producto vendido
    private int idProducto;

    // Constructor vacío
    public DetalleVenta() {
    }

    // Constructor con todos los atributos
    public DetalleVenta(
            int idDetalleVenta,
            int cantidad,
            double precioUnitario,
            double subtotal,
            int idVenta,
            int idProducto) {

        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
    }

    // Getter del ID
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    // Setter del ID
    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    // Getter de la cantidad
    public int getCantidad() {
        return cantidad;
    }

    // Setter de la cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter del precio unitario
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    // Setter del precio unitario
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Getter del subtotal
    public double getSubtotal() {
        return subtotal;
    }

    // Setter del subtotal
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Getter del ID de venta
    public int getIdVenta() {
        return idVenta;
    }

    // Setter del ID de venta
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    // Getter del ID de producto
    public int getIdProducto() {
        return idProducto;
    }

    // Setter del ID de producto
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}