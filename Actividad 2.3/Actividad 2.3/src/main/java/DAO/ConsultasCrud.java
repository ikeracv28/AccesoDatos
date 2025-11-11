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
                String sql = "INSERT INTO proyectos (nombre, presupuesto) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, "Proyecto quimica");
                ps.setDouble(2, 20100.00);
                int filas = ps.executeUpdate();

                ps.setString(1, "Proyecto IA");
                ps.setDouble(2, 15100.00);
                int filas2 = ps.executeUpdate();

                ps.setString(1, "Proyecto guapardo");
                ps.setDouble(2, 100000.00);
                int fila3 = ps.executeUpdate();


                System.out.println("Filas insertadas: " + filas + filas2 + fila3);
                mostrarProyectos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConexionHikari.cerrarPool();
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
                } else if{
            }else {
                System.out.println("No se ha encontrado la conexion");
            }




                ps.setString(1, "Proyecto IA");
                ps.setDouble(2, 15100.00);
                int filas2 = ps.executeUpdate();

                ps.setString(1, "Proyecto guapardo");
                ps.setDouble(2, 100000.00);
                int fila3 = ps.executeUpdate();


                System.out.println("Filas insertadas: " + filas + filas2 + fila3);
                mostrarProyectos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConexionHikari.cerrarPool();
        }
    }

    public void mostrarProyectos(){
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "UPDATE empleados SET salario = ? WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDouble(1, 2200.00);
                ps.setInt(2, 1);
                int filas = ps.executeUpdate();
                System.out.println("Filas actualizadas: " + filas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConexionHikari.cerrarPool();
        }
    }
    }





/*
            if(con2 != null){
                // Confirmación sencilla de que la conexión fue obtenida correctamente.

                Statement st = con2.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== EMPLEADOS ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));

                }
                System.out.println("Conexión2 obtenida del pool correctamente.");
            }
            if(con3 != null){
                // Confirmación sencilla de que la conexión fue obtenida correctamente.

                Statement st = con3.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== EMPLEADOS ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                }
                System.out.println("Conexión3 obtenida del pool correctamente.");
            }


        } catch (SQLException e) {
            /*
             * Captura específica de SQLException: en un contexto real se podría
             * registrar el error en un logger y tomar decisiones según el código SQLState.
             * Aquí, por simplicidad didáctica, se imprime la traza de la excepción.
             */
        //e.printStackTrace();
/*

            /*
             * El pool de conexiones (DataSource gestionado por HikariCP) debe cerrarse
             * explícitamente cuando la aplicación ya no lo necesita. Llamar a
             * ConexionDB_HikariCP.cerrarPool() garantiza la liberación ordenada de
             * recursos del pool (hilos, conexiones físicas, etc.).
             *
             * Nota académica: en aplicaciones long-running (servidores) el cierre del
             * pool suele gestionarse en el shutdown hook o en el lifecycle del contenedor,
             * no inmediatamente después de cada operación.


 */

