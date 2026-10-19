package com.sivet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase encargada de conectar SIVET con PostgreSQL
public class ConexionDB {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/bd_sivet";

    private static final String USUARIO = "postgres";

    private static final String CONTRASENA =
            "Postgre25";

    // Obtiene una conexión normal
    public static Connection obtenerConexion() {

        try {
            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            System.out.println(
                    "Conexión a PostgreSQL exitosa."
            );

            return conexion;

        } catch (SQLException e) {

            System.out.println(
                    "Error al conectar con PostgreSQL."
            );

            System.out.println(
                    e.getMessage()
            );

            return null;
        }
    }

    // Obtiene una conexión preparada para trabajar con transacciones
    public static Connection obtenerConexionTransaccion()
            throws SQLException {

        Connection conexion = DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );

        // Desactiva el autocommit.
        // Desde aquí podremos hacer commit o rollback manualmente.
        conexion.setAutoCommit(false);

        return conexion;
    }
}