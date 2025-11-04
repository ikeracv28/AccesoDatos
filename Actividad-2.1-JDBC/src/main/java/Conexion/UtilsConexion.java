package Conexion;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class UtilsConexion {
    private static String url;
    private static String user;
    private static String password;
    private static Scanner sc = new Scanner(System.in);


    public static String[] cargarDatos(){
        String [] parametros;
        // 1. Cargar configuración desde db.properties
        Properties props = new Properties();
            try (InputStream input = UtilsConexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("❌ No se encontró el archivo db.properties");
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();

        }

    // 2. Obtener datos de conexión
    url = props.getProperty("db.url");
    user = props.getProperty("db.user");
    password = props.getProperty("db.password");
        parametros = new String[]{url, user, password} ;
        return parametros;
    }

    // 3. Probar conexión
    public static void probarConexion(){
        cargarDatos();
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Conexión establecida con éxito a la base de datos.");

            // Mostrar metadatos
            DatabaseMetaData meta = con.getMetaData();
            System.out.println("🔹 Driver: " + meta.getDriverName());
            System.out.println("🔹 Versión del driver: " + meta.getDriverVersion());
            System.out.println("🔹 Base de datos: " + meta.getDatabaseProductName());
            System.out.println("🔹 Versión BD: " + meta.getDatabaseProductVersion());
            System.out.println("🔹 Usuario conectado: " + meta.getUserName());
            System.out.println("🔹 URL de conexión: " + meta.getURL());

        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    }

