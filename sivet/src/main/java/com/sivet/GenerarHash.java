package com.sivet;

import com.sivet.service.PasswordService;

import java.util.Scanner;

public class GenerarHash {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PasswordService passwordService = new PasswordService();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        String hash = passwordService.generarHash(contrasena);

        System.out.println();
        System.out.println("Hash BCrypt:");
        System.out.println(hash);

        scanner.close();
    }
}