package com.sivet.model;

import java.time.LocalDateTime;

// Clase que representa la tabla VENTA de PostgreSQL
public class Venta {

    // Identificador de la venta
    private int idVenta;

    // Fecha y hora de la venta
    private LocalDateTime fecha;

    // Total de la venta
    private double total;

    // Usuario que registra la venta
    private int idUsuario;

    // Cliente de la venta
    // Puede ser null porque en PostgreSQL es opcional
    private Integer idCliente;

    // Constructor vacío
    public Venta() {
    }

    // Constructor con todos los atributos
    public Venta(
            int idVenta,
            LocalDateTime fecha,
            double total,
            int idUsuario,
            Integer idCliente) {

        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
    }

    // Getter del ID
    public int getIdVenta() {
        return idVenta;
    }

    // Setter del ID
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
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

    // Getter del ID del cliente
    public Integer getIdCliente() {
        return idCliente;
    }

    // Setter del ID del cliente
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}