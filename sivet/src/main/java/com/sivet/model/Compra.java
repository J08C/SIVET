package com.sivet.model;

import java.time.LocalDateTime;

// Clase que representa la tabla COMPRA de PostgreSQL
public class Compra {

    // Identificador de la compra
    private int idCompra;

    // Fecha y hora de la compra
    private LocalDateTime fecha;

    // Total de la compra
    private double total;

    // Usuario que registra la compra
    private int idUsuario;

    // Proveedor de la compra
    private int idProveedor;

    // Constructor vacío
    public Compra() {
    }

    // Constructor con todos los atributos
    public Compra(
            int idCompra,
            LocalDateTime fecha,
            double total,
            int idUsuario,
            int idProveedor) {

        this.idCompra = idCompra;
        this.fecha = fecha;
        this.total = total;
        this.idUsuario = idUsuario;
        this.idProveedor = idProveedor;
    }

    // Getter del ID
    public int getIdCompra() {
        return idCompra;
    }

    // Setter del ID
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    // Getter de la fecha
    public LocalDateTime getFecha() {
        return fecha;
    }

    // Setter de la fecha
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    // Getter del total
    public double getTotal() {
        return total;
    }

    // Setter del total
    public void setTotal(double total) {
        this.total = total;
    }

    // Getter del ID del usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    // Setter del ID del usuario
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Getter del ID del proveedor
    public int getIdProveedor() {
        return idProveedor;
    }

    // Setter del ID del proveedor
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
}