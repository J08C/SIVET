package com.sivet.view;

import com.sivet.controller.AuthController;
import com.sivet.model.Usuario;

import java.util.Scanner;

/**
 * Vista encargada del inicio de sesión.
 */
public class LoginView {

    private final AuthController authController;
    private final Scanner scanner;

    public LoginView(
            AuthController authController,
            Scanner scanner) {

        this.authController = authController;
        this.scanner = scanner;
    }

    /**
     * Solicita las credenciales al usuario.
     *
     * @return Usuario autenticado o null
     */
    public Usuario iniciarSesion() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("          INICIO DE SESIÓN");
        System.out.println("======================================");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario usuarioAutenticado =
                authController.iniciarSesion(
                        usuario,
                        contrasena
                );

        if (usuarioAutenticado == null) {

            System.out.println();
            System.out.println(
                    "Usuario o contraseña incorrectos."
            );

            return null;
        }

        System.out.println();
        System.out.println(
                "Inicio de sesión correcto."
        );

        System.out.println(
                "Bienvenido: "
                        + usuarioAutenticado.getNombreCompleto()
        );

        System.out.println(
                "ID de rol: "
                        + usuarioAutenticado.getIdRol()
        );

        return usuarioAutenticado;
    }
}