package com.sivet;

import com.sivet.controller.AuthController;
import com.sivet.dao.UsuarioDAO;
import com.sivet.dao.UsuarioDAOImpl;
import com.sivet.service.AuthService;
import com.sivet.service.PasswordService;
import com.sivet.view.LoginView;
import com.sivet.view.MenuView;

import java.util.Scanner;

// Clase principal de SIVET
// Es el punto de entrada de la aplicación.
public class Main {

    public static void main(String[] args) {

        // =========================================
        // CONFIGURACIÓN DEL LOGIN
        // =========================================

        UsuarioDAO usuarioDAO =
                new UsuarioDAOImpl();

        PasswordService passwordService =
                new PasswordService();

        AuthService authService =
                new AuthService(
                        usuarioDAO,
                        passwordService
                );

        AuthController authController =
                new AuthController(authService);

        Scanner scanner =
                new Scanner(System.in);

        LoginView loginView =
                new LoginView(
                        authController,
                        scanner
                );

        // =========================================
        // INICIO DE SESIÓN
        // =========================================

        var usuarioAutenticado =
                loginView.iniciarSesion();

        // Si las credenciales son incorrectas,
        // no ingresamos al sistema.
        if (usuarioAutenticado == null) {

            scanner.close();
            return;
        }

        // =========================================
        // MENÚ PRINCIPAL
        // =========================================

        MenuView menuView =
                new MenuView(usuarioAutenticado);

        menuView.mostrarMenu();

        scanner.close();
    }
}