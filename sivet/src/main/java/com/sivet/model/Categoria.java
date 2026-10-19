package com.sivet.model;

// Clase que representa la tabla CATEGORIA de PostgreSQL
public class Categoria {

    // Identificador de la categoría
    private int idCategoria;

    // Nombre de la categoría
    private String nombre;

    // Constructor vacío
    public Categoria() {
    }

    // Constructor con todos los atributos
    public Categoria(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    // Getter del ID
    public int getIdCategoria() {
        return idCategoria;
    }

    // Setter del ID
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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