package com.sivet;

import com.sivet.dao.ConexionDB;
import java.sql.Connection;

// Clase para probar la conexión de SIVET con PostgreSQL
public class PruebaConexion {

    public static void main(String[] args) {

        // Intentamos obtener una conexión
        Connection conexion = ConexionDB.obtenerConexion();

        // Verificamos si la conexión fue exitosa
        if (conexion != null) {

            System.out.println(
                    "SIVET está conectado a bd_sivet."
            );

        } else {

            System.out.println(
                    "No se pudo conectar a bd_sivet."
            );
        }
    }
}