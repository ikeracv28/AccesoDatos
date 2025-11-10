package Conexion;

import java.sql.*;
import static Conexion.UtilsConexion.*;
import java.util.Scanner;

public class ConsultasSQL {
    private static String url;
    private static String user;
    private static String password;

    public ConsultasSQL() {
        String [] params = cargarDatos();
        url = params[0];
        user = params[1];
        password = params[2];
    }
    private static Scanner sc = new Scanner(System.in);

    public void mostrarEmpleados() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM empleados");

            System.out.println("\n=== EMPLEADOS ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarEmpleadoPorID() {

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM empleados WHERE id = ?");

            System.out.println("Dime el id del usuario que quieres buscar: ");
            int id = sc.nextInt();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=== EMPLEADOS ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void llamarAProcedimiento(){
        try(Connection con = DriverManager.getConnection(url, user, password)) {
            CallableStatement cs = con.prepareCall("{call obtener_empleado(?)}");
            cs.setInt(1, 1);


            ResultSet rs = cs.executeQuery();

            System.out.println("\n=== EMPLEADOS con Callable===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s ",
                        rs.getInt("id"), rs.getString("nombre"));
            }


    }catch (SQLException e){
        throw new RuntimeException(e);}
    }
}
