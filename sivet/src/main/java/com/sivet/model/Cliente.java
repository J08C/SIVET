package com.sivet.model;

// Clase que representa la tabla CLIENTE de PostgreSQL
public class Cliente {

    // Identificador del cliente
    private int idCliente;

    // DNI o RUC del cliente
    private String dniRuc;

    // Nombres del cliente
    private String nombres;

    // Teléfono del cliente
    private String telefono;

    // Dirección del cliente
    private String direccion;

    // Constructor vacío
    public Cliente() {
    }

    // Constructor con todos los atributos
    public Cliente(
            int idCliente,
            String dniRuc,
            String nombres,
            String telefono,
            String direccion) {

        this.idCliente = idCliente;
        this.dniRuc = dniRuc;
        this.nombres = nombres;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getter del ID
    public int getIdCliente() {
        return idCliente;
    }

    // Setter del ID
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Getter del DNI/RUC
    public String getDniRuc() {
        return dniRuc;
    }

    // Setter del DNI/RUC
    public void setDniRuc(String dniRuc) {
        this.dniRuc = dniRuc;
    }

    // Getter de los nombres
    public String getNombres() {
        return nombres;
    }

    // Setter de los nombres
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    // Getter del teléfono
    public String getTelefono() {
        return telefono;
    }

    // Setter del teléfono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Getter de la dirección
    public String getDireccion() {
        return direccion;
    }

    // Setter de la dirección
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}