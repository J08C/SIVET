package com.sivet.model;

import java.time.LocalDateTime;

// Clase que representa la tabla MOVIMIENTO_INVENTARIO
// de PostgreSQL
public class MovimientoInventario {

    // Identificador del movimiento
    private int idMovimiento;

    // Fecha y hora del movimiento
    private LocalDateTime fecha;

    // Tipo de movimiento: ENTRADA o SALIDA
    private String tipo;

    // Cantidad del movimiento
    private int cantidad;

    // Saldo de stock después del movimiento
    private int saldo;

    // Motivo del movimiento
    private String motivo;

    // Producto relacionado con el movimiento
    private int idProducto;

    // Usuario que realizó el movimiento
    private int idUsuario;

    // Constructor vacío
    public MovimientoInventario() {
    }

    // Constructor con todos los atributos
    public MovimientoInventario(
            int idMovimiento,
            LocalDateTime fecha,
            String tipo,
            int cantidad,
            int saldo,
            String motivo,
            int idProducto,
            int idUsuario) {

        this.idMovimiento = idMovimiento;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.saldo = saldo;
        this.motivo = motivo;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
    }

    // Getter del ID
    public int getIdMovimiento() {
        return idMovimiento;
    }

    // Setter del ID
    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    // Getter de la fecha
    public LocalDateTime getFecha() {
        return fecha;
    }

    // Setter de la fecha
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    // Getter del tipo
    public String getTipo() {
        return tipo;
    }

    // Setter del tipo
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter de la cantidad
    public int getCantidad() {
        return cantidad;
    }

    // Setter de la cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter del saldo
    public int getSaldo() {
        return saldo;
    }

    // Setter del saldo
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    // Getter del motivo
    public String getMotivo() {
        return motivo;
    }

    // Setter del motivo
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    // Getter del ID del producto
    public int getIdProducto() {
        return idProducto;
    }

    // Setter del ID del producto
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    // Getter del ID del usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    // Setter del ID del usuario
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}