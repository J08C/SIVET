package com.sivet.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {

    @Test
    void debeGenerarHashDeContrasena() {

        PasswordService passwordService =
                new PasswordService();

        String hash =
                passwordService.generarHash("123456");

        assertNotNull(hash);
        assertFalse(hash.isBlank());
        assertNotEquals("123456", hash);
    }

    @Test
    void debeVerificarContrasenaCorrecta() {

        PasswordService passwordService =
                new PasswordService();

        String hash =
                passwordService.generarHash("123456");

        boolean resultado =
                passwordService.verificar(
                        "123456",
                        hash
                );

        assertTrue(resultado);
    }

    @Test
    void debeRechazarContrasenaIncorrecta() {

        PasswordService passwordService =
                new PasswordService();

        String hash =
                passwordService.generarHash("123456");

        boolean resultado =
                passwordService.verificar(
                        "999999",
                        hash
                );

        assertFalse(resultado);
    }

    @Test
    void debeRechazarContrasenaVacia() {

        PasswordService passwordService =
                new PasswordService();

        assertThrows(
                IllegalArgumentException.class,
                () -> passwordService.generarHash("")
        );
    }
}