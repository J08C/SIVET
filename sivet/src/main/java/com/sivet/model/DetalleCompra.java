package com.sivet.model;

// Clase que representa la tabla DETALLE_COMPRA de PostgreSQL
public class DetalleCompra {

    // Identificador del detalle
    private int idDetalleCompra;

    // Cantidad de productos comprados
    private int cantidad;

    // Costo unitario del producto
    private double costoUnitario;

    // Subtotal del detalle
    private double subtotal;

    // ID de la compra
    private int idCompra;

    // ID del producto comprado
    private int idProducto;

    // Constructor vacío
    public DetalleCompra() {
    }

    // Constructor con todos los atributos
    public DetalleCompra(
            int idDetalleCompra,
            int cantidad,
            double costoUnitario,
            double subtotal,
            int idCompra,
            int idProducto) {

        this.idDetalleCompra = idDetalleCompra;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.subtotal = subtotal;
        this.idCompra = idCompra;
        this.idProducto = idProducto;
    }

    // Getter del ID
    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    // Setter del ID
    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    // Getter de la cantidad
    public int getCantidad() {
        return cantidad;
    }

    // Setter de la cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter del costo unitario
    public double getCostoUnitario() {
        return costoUnitario;
    }

    // Setter del costo unitario
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    // Getter del subtotal
    public double getSubtotal() {
        return subtotal;
    }

    // Setter del subtotal
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // Getter del ID de compra
    public int getIdCompra() {
        return idCompra;
    }

    // Setter del ID de compra
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
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