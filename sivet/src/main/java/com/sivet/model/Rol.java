package com.sivet.model;

// Clase que representa la tabla ROL de PostgreSQL
public class Rol {

    // Identificador del rol
    private int idRol;

    // Nombre del rol
    private String nombre;

    // Constructor vacío
    public Rol() {
    }

    // Constructor con todos los atributos
    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    // Getter del ID
    public int getIdRol() {
        return idRol;
    }

    // Setter del ID
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    // Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Setter del nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}