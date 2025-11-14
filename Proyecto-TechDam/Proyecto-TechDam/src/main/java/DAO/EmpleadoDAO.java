package DAO;

import Modelo.Empleado;
import Modelo.Proyecto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class EmpleadoDAO {

    Empleado empleado = new Empleado();
    static Scanner sc = new Scanner(System.in);

    static List<Empleado> listaEmpleados = new ArrayList<>();



    public List<Empleado> obtenerEmpleados() {
        try (Connection con = getConexion()) {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== Empleados ===");
                while (rs.next()) {
                    Empleado e = new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("departamento"),rs.getBigDecimal("salario"), rs.getBoolean("activo"));
                    listaEmpleados.add(e);
//                    System.out.printf("ID: %d | Nombre: %s | Presupuesto: %.2f €%n",
//                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("presupuesto"));


                }
                return listaEmpleados;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mostrarEmpleados() {
        try (Connection con = getConexion()) {
            if (con != null) {
                obtenerEmpleados();
                for(Empleado empleado : listaEmpleados){
                    System.out.println(empleado);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
