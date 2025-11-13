package DAO;

import java.sql.*;

import java.time.LocalDate;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class AsignacionesDAO {
    static Scanner sc = new Scanner(System.in);

    public void llamarProcedimiento() {
        try (Connection con = getConexion()) {
            if (con != null) {
                CallableStatement cs = con.prepareCall("{ CALL asignarEmpleadoAProyecto(?, ?, ?) }");
                System.out.println("Dime el id del empleado que quieres asignar al proyecto: ");
                int idEmpleado = sc.nextInt();
                sc.nextLine();

                System.out.println("Dime el id del proyecto al que quieres asignar el empleado: ");
                int idProyecto = sc.nextInt();
                sc.nextLine();
                cs.setInt(1, idEmpleado);
                cs.setInt(2, idProyecto);
                cs.setDate(3, Date.valueOf(LocalDate.now())); // fecha_asignacion

                cs.execute();

                System.out.println("Empleado asignado correctamente al proyecto.");
            }  else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }

            }catch(Exception e){
            throw new RuntimeException(e);

            }
            }

    public void mostrarAsignaciones(){
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM asignaciones");

                System.out.println("\n=== Asignaciones ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | ID_EMPLEADO: %d | ID_PROYECTO: %d | FECHA ASIGNACION: %s",
                            rs.getInt("id"), rs.getInt("id_empleado"), rs.getInt("id_proyecto"),  rs.getDate("fecha_asignacion"));
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        }





