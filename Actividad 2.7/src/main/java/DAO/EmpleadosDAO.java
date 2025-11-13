package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class EmpleadosDAO {
    static Scanner sc = new Scanner(System.in);

    public void mostrarEmpleados(){
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== Empleados ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insertarProyectos() {

        try (Connection con = getConexion()) {
            if (con != null) {
                System.out.println("Dime el Nombre del Empleado: ");
                String nombre = sc.nextLine();
                System.out.println("Dime el salario del empleado: ");
                int salario = sc.nextInt();
                sc.nextLine();

                String sql = "INSERT INTO empleados (nombre, salario) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, nombre);
                ps.setDouble(2, salario );
                int filas = ps.executeUpdate();

//                ps.setString(1, "Proyecto IA");
//                ps.setDouble(2, 15100.00);
//                int filas2 = ps.executeUpdate();
//
//                ps.setString(1, "Proyecto guapardo");
//                ps.setDouble(2, 100000.00);
//                int fila3 = ps.executeUpdate();


                System.out.println("Filas insertadas: " + filas);
                mostrarEmpleados();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hacerTransferencia(){
        // Obtiene una conexión desde la clase utilitaria ConexionBD y la cierra automáticamente
        try (Connection con = getConexion()) {
            // Desactiva el auto-commit para manejar la transacción manualmente
            con.setAutoCommit(false);

            // PreparedStatement para retirar dinero: resta saldo a la cuenta indicada
            PreparedStatement retirar = con.prepareStatement("UPDATE proyectos SET presupuesto = presupuesto - ? WHERE id = ?");

            System.out.println("Dime el id del proyecto al que descontarle el presupuesto: ");
            int idPresupuesto = sc.nextInt();
            sc.nextLine();

            System.out.println("Dime el dinero que quieres retirarle: ");
            int dineroRetirar = sc.nextInt();
            sc.nextLine();


            // PreparedStatement para ingresar dinero: suma saldo a la cuenta indicada
            PreparedStatement ingresar = con.prepareStatement("UPDATE empleados SET salario = salario + ? WHERE id = ?");

            System.out.println("Dime el id del Empleado al que sumarle el salario: ");
            int idEmpleado = sc.nextInt();
            sc.nextLine();



            // Configura el primer parámetro (cantidad) y el segundo (id) para la primera actualización
            retirar.setDouble(1, dineroRetirar);
            retirar.setInt(2, idPresupuesto);
            // Ejecuta la actualización que retira dinero de la cuenta 1
            retirar.executeUpdate();

            // Configura los parámetros para la segunda actualización
            ingresar.setDouble(1, dineroRetirar);
            ingresar.setInt(2, idEmpleado);
            // Ejecuta la actualización que ingresa dinero en la cuenta 2
            ingresar.executeUpdate();

            // Confirma la transacción: ambas actualizaciones se hacen permanentes
            con.commit();
            System.out.println("Transacción realizada con éxito.");

        } catch (Exception e) {
            // Si ocurre cualquier excepción, imprime la traza
            e.printStackTrace();
            try {
                // Nota: aquí se llama a ConexionBD.getConexion().rollback()
                // Esto obtiene una nueva conexión y llama rollback en ella, lo cual no revierte
                // la transacción de la conexión original. Se mantiene la lógica original sin cambiarla.
                getConexion().rollback();
            } catch (Exception ex) {
                // Imprime la traza si el rollback falla
                ex.printStackTrace();
            }
        }
    }

}
