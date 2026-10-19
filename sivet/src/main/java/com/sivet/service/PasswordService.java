package com.sivet.service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servicio encargado de cifrar y verificar contraseñas.
 */
public class PasswordService {

    /**
     * Genera un hash BCrypt para una contraseña.
     *
     * @param contrasena contraseña en texto plano
     * @return contraseña convertida en hash
     */
    public String generarHash(String contrasena) {

        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña no puede estar vacía."
            );
        }

        return BCrypt.hashpw(
                contrasena,
                BCrypt.gensalt()
        );
    }

    /**
     * Verifica una contraseña contra un hash BCrypt.
     *
     * @param contrasena contraseña ingresada
     * @param hash hash almacenado en la base de datos
     * @return true si coinciden
     */
    public boolean verificar(
            String contrasena,
            String hash) {

        if (contrasena == null || hash == null) {
            return false;
        }

        if (contrasena.isBlank() || hash.isBlank()) {
            return false;
        }

        return BCrypt.checkpw(
                contrasena,
                hash
        );
    }
}