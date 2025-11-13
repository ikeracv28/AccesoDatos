package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class ProyectosDAO {
    static Scanner sc = new Scanner(System.in);


    public void mostrarProyectos(){
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM proyectos");

                System.out.println("\n=== Proyectos ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Presupuesto: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("presupuesto"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarProyectos() {

        try (Connection con = getConexion()) {
            if (con != null) {
                System.out.println("Dime el Nombre del proyecto: ");
                String nombre = sc.nextLine();
                System.out.println("Dime el presupuesto del proyecto: ");
                int presupuesto = sc.nextInt();
                sc.nextLine();

                String sql = "INSERT INTO proyectos (nombre, presupuesto) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nombre);
                ps.setDouble(2, presupuesto );
                int filas = ps.executeUpdate();

//                ps.setString(1, "Proyecto IA");
//                ps.setDouble(2, 15100.00);
//                int filas2 = ps.executeUpdate();
//
//                ps.setString(1, "Proyecto guapardo");
//                ps.setDouble(2, 100000.00);
//                int fila3 = ps.executeUpdate();


                System.out.println("Filas insertadas: " + filas);
                mostrarProyectos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
