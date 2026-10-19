package com.sivet.model;

// Clase que representa la tabla USUARIO de PostgreSQL
public class Usuario {

    // Identificador del usuario
    private int idUsuario;

    // Nombre completo del usuario
    private String nombreCompleto;

    // Nombre de usuario para iniciar sesión
    private String usuario;

    // Contraseña almacenada como hash
    private String contrasenaHash;

    // ID del rol asociado al usuario
    private int idRol;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con todos los atributos
    public Usuario(
            int idUsuario,
            String nombreCompleto,
            String usuario,
            String contrasenaHash,
            int idRol) {

        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.contrasenaHash = contrasenaHash;
        this.idRol = idRol;
    }

    // Getter del ID
    public int getIdUsuario() {
        return idUsuario;
    }

    // Setter del ID
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Getter del nombre completo
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    // Setter del nombre completo
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    // Getter del usuario
    public String getUsuario() {
        return usuario;
    }

    // Setter del usuario
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Getter de la contraseña hash
    public String getContrasenaHash() {
        return contrasenaHash;
    }

    // Setter de la contraseña hash
    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    // Getter del ID del rol
    public int getIdRol() {
        return idRol;
    }

    // Setter del ID del rol
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}