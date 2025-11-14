package DAO;

import Modelo.Proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class ProyectoDAO {

    Proyecto proyecto = new Proyecto();
    static Scanner sc = new Scanner(System.in);

    static List<Proyecto> listaProyecto = new ArrayList<>();



    public List<Proyecto> obtenerProyectos(){
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM proyectos");

                System.out.println("\n=== Proyectos ===");
                while (rs.next()) {
                    Proyecto p = new Proyecto(rs.getInt("id_proyecto"), rs.getString("nombre"), rs.getBigDecimal("presupuesto"));
                    listaProyecto.add(p);
//                    System.out.printf("ID: %d | Nombre: %s | Presupuesto: %.2f €%n",
//                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("presupuesto"));


                }
                return listaProyecto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Proyecto> obtenerPorId(int id) {
        String sql = "SELECT id_proyecto, nombre, presupuesto FROM proyectos WHERE id_proyecto = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Proyecto p = new Proyecto(
                        rs.getInt("id_proyecto"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("presupuesto")
                );
                return Optional.of(p);

            }

            return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }



    public void mostrarProyectos() {
        try (Connection con = getConexion()) {
            if (con != null) {
                obtenerProyectos();
                for(Proyecto proyecto : listaProyecto){
                    System.out.println(proyecto);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearProyectos(Proyecto proyecto) {

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
