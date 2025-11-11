package DAO;

import java.sql.*;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class ConsultasDAO {
    static Scanner sc = new Scanner(System.in);

    public void mostrarEmpleados() {
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== Proyectos ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarMetaDataCOnResulSet() {
        try (Connection con = getConexion()) {
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM empleados");
        ResultSetMetaData meta = rs.getMetaData();

            System.out.println("=============== Datos con ResultSetMetaData ===================");

        int columnas = meta.getColumnCount();
        System.out.println("Número de columnas: " + columnas);

        for (int i = 1; i <= columnas; i++) {
            System.out.println("Columna " + i + ": " +
                    meta.getColumnName(i) +
                    " (" + meta.getColumnTypeName(i) + ")");
        }
        }catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void mostrarMetaDataConDatabaseMetaData(){
        try (Connection con = getConexion()) {
            DatabaseMetaData dbMeta = con.getMetaData();

            System.out.println("=============== Datos con DatabaseMetaData ================");

            System.out.println("Producto BD: " + dbMeta.getDatabaseProductName());
            System.out.println("Versión: " + dbMeta.getDatabaseProductVersion());
            System.out.println("Driver: " + dbMeta.getDriverName());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}