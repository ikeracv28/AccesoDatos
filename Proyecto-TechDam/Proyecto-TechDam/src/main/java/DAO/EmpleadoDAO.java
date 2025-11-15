package DAO;

import Modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class EmpleadoDAO {

    Empleado empleado = new Empleado();
    static Scanner sc = new Scanner(System.in);

    static List<Empleado> listaEmpleado = new ArrayList<>();

    // Método para crear un nuevo empleado
    public int crearEmpleado(Empleado empleado) {

        int idGenerado = -1; // Inicializamos el ID como un valor no válido
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "INSERT INTO empleados (nombre, departamento, salario, activo) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    // Seteamos los valores
                    ps.setString(1, empleado.getNombre());
                    ps.setString(2, empleado.getDepartamento());
                    ps.setBigDecimal(3, empleado.getSalario());
                    ps.setBoolean(4, empleado.isActivo());

                    // Ejecutamos la inserción
                    int filas = ps.executeUpdate();

                    // Obtenemos el ID generado
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            idGenerado = rs.getInt(1);  // Recuperamos el primer valor de la columna generada (ID)
                        }
                    }

                    System.out.println("Filas insertadas: " + filas);
                    System.out.println("ID del empleado creado: " + idGenerado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    // Método para obtener todos los empleados
    public List<Empleado> obtenerEmpleados() {
        try (Connection con = getConexion()) {
            if (con != null) {
                listaEmpleado.clear();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== Empleados ===");
                while (rs.next()) {
                    Empleado e = new Empleado(
                            rs.getInt("id_empleado"),
                            rs.getString("nombre"),
                            rs.getString("departamento"),
                            rs.getBigDecimal("salario"),
                            rs.getBoolean("activo")
                    );
                    listaEmpleado.add(e);
                }
                return listaEmpleado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener un empleado por su ID
    public Optional<Empleado> obtenerPorId(int id) {
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "SELECT id_empleado, nombre, departamento, salario, activo FROM empleados WHERE id_empleado = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Empleado e = new Empleado(
                            rs.getInt("id_empleado"),
                            rs.getString("nombre"),
                            rs.getString("departamento"),
                            rs.getBigDecimal("salario"),
                            rs.getBoolean("activo")
                    );
                    return Optional.of(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Método para mostrar todos los empleados
    public void mostrarEmpleados() {
        try (Connection con = getConexion()) {
            if (con != null) {
                for (Empleado empleado : listaEmpleado) {
                    System.out.println(empleado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos de un empleado
    public boolean actualizarEmpleado(Empleado empleado) {
        boolean exito = false;
        String sql = "UPDATE empleados SET nombre = ?, departamento = ?, salario = ?, activo = ? WHERE id_empleado = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, empleado.getNombre());
                    ps.setString(2, empleado.getDepartamento());
                    ps.setBigDecimal(3, empleado.getSalario());
                    ps.setBoolean(4, empleado.isActivo());
                    ps.setInt(5, empleado.getId_empleado());

                    int filas = ps.executeUpdate();
                    exito = filas > 0; // Si se actualizó al menos una fila, el éxito es verdadero
                    System.out.println("Filas actualizadas: " + filas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exito;
    }

    // Método para eliminar un empleado
    public boolean eliminarEmpleado(int idEmpleado) {
        boolean exito = false;
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idEmpleado);
                    int filas = ps.executeUpdate();
                    exito = filas > 0; // Si se eliminó al menos una fila, el éxito es verdadero
                    System.out.println("Filas eliminadas: " + filas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exito;
    }
}
