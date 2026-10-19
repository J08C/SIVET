package com.sivet.model;

// Clase que representa la tabla PROVEEDOR de PostgreSQL
public class Proveedor {

    // Identificador del proveedor
    private int idProveedor;

    // RUC del proveedor
    private String ruc;

    // Razón social del proveedor
    private String razonSocial;

    // Teléfono del proveedor
    private String telefono;

    // Correo electrónico del proveedor
    private String correo;

    // Dirección del proveedor
    private String direccion;

    // Indica si el proveedor está activo
    private boolean estado;

    // Constructor vacío
    public Proveedor() {
    }

    // Constructor con todos los atributos
    public Proveedor(
            int idProveedor,
            String ruc,
            String razonSocial,
            String telefono,
            String correo,
            String direccion,
            boolean estado) {

        this.idProveedor = idProveedor;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.estado = estado;
    }

    // Getter del ID
    public int getIdProveedor() {
        return idProveedor;
    }

    // Setter del ID
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    // Getter del RUC
    public String getRuc() {
        return ruc;
    }

    // Setter del RUC
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    // Getter de la razón social
    public String getRazonSocial() {
        return razonSocial;
    }

    // Setter de la razón social
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    // Getter del teléfono
    public String getTelefono() {
        return telefono;
    }

    // Setter del teléfono
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Getter del correo
    public String getCorreo() {
        return correo;
    }

    // Setter del correo
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Getter de la dirección
    public String getDireccion() {
        return direccion;
    }

    // Setter de la dirección
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Getter del estado
    public boolean isEstado() {
        return estado;
    }

    // Setter del estado
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}