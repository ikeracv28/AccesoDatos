package DAO;
import Conexion.ConexionHikari;

import static Conexion.ConexionHikari.*;

import java.sql.*;
import java.util.Scanner;


/**
 * Ejemplo sencillo que demuestra cómo obtener una conexión desde un pool
 * (HikariCP, representado por la clase ConexionDB_HikariCP) y cómo liberar
 * los recursos del pool al finalizar la aplicación.
 *
 * Propósito académico:
 * - Mostrar el uso de try-with-resources para gestionar la conexión.
 * - Ilustrar el manejo básico de SQLException.
 * - Explicar el cierre explícito del pool al terminar el programa.
 *
 * No se modifica la lógica original; sólo se añaden comentarios explicativos.
 */
public class ConsultasCrud {
    static Scanner sc = new Scanner(System.in);

    /**
     * Punto de entrada de la aplicación de ejemplo.
     * <p>
     * Flujo:
     * 1. Se solicita una conexión al pool mediante ConexionDB_HikariCP.getConexion().
     * 2. Si la obtención es correcta, se muestra un mensaje de confirmación.
     * 3. Cualquier SQLException producida se captura e imprime la traza.
     * 4. En el bloque finally se invoca ConexionDB_HikariCP.cerrarPool() para
     * liberar los recursos del pool antes de finalizar la JVM.
     */
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

    public void actualizarProyectos() {

        try (Connection con = getConexion()) {
            if (con != null) {
                System.out.println("Dime el id de proyecto quieres actualizar: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.println("Dime si quieres actualizar el nombre o el presupuesto: ");
                String eleccion = sc.nextLine();

                if(eleccion.equalsIgnoreCase("nombre")) {
                    System.out.println("Dime el nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();

                    String sql = "UPDATE proyectos SET nombre = ? WHERE id = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, nuevoNombre);
                    ps.setDouble(2, id);
                    int filas = ps.executeUpdate();

                    System.out.println("Filas actualizadas: " + filas);
                } else if(eleccion.equalsIgnoreCase("presupuesto")) {
                    System.out.println("Dime el nuevo presupuesto: ");
                    String nuevoPresupuesto = sc.nextLine();

                    String sql = "UPDATE proyectos SET presupuesto = ? WHERE id = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, nuevoPresupuesto);
                    ps.setDouble(2, id);
                    int filas = ps.executeUpdate();

                    System.out.println("Filas actualizadas: " + filas);
            }else {
                System.out.println("No se ha encontrado la conexion");
            }
                mostrarProyectos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eliminarProyectos() {

        try (Connection con = getConexion()) {
            if (con != null) {
                System.out.println("Dime el id de proyecto quieres eliminar: ");
                int id = sc.nextInt();
                sc.nextLine();

                String sql = "DELETE FROM proyectos WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, id);
                int filas = ps.executeUpdate();

                System.out.println("Filas eliminadas: " + filas);
                }else {
                    System.out.println("No se ha encontrado la conexion");
                }
                mostrarProyectos();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
    }





