package DAO;

import Modelo.Cliente;
import Modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static Conexion.ConexionHikari.getConexion;

public class ClienteDAO {

    Cliente cliente = new Cliente();
    static Scanner sc = new Scanner(System.in);

    static List<Cliente> listaClientes = new ArrayList<>();

    // Método para crear un nuevo empleado
    public int crearCliente(Cliente cliente) {

        int idGenerado = -1; // Inicializamos el ID como un valor no válido
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "INSERT INTO Cliente (nombre, edad, id_venta) VALUES (?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    // Seteamos los valores
                    ps.setString(1, cliente.getNombre());
                    ps.setInt(2, cliente.getEdad());
                    ps.setInt(3, cliente.getId_venta());


                    // Ejecutamos la inserción
                    int filas = ps.executeUpdate();

                    // Obtenemos el ID generado
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            idGenerado = rs.getInt(1);  // Recuperamos el primer valor de la columna generada (ID)
                        }
                    }

                    System.out.println("Filas insertadas: " + filas);
                    // para que nos devuleva el ID del empleado creado
                    System.out.println("ID del Cliente creado: " + idGenerado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    // Método para obtener todos los empleados
    public List<Cliente> obtenerCliente() {
        try (Connection con = getConexion()) {
            if (con != null) {
                listaClientes.clear();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Cliente");

                System.out.println("\n=== Cliente ===");
                while (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            rs.getInt("id_venta")
                    );
                    listaClientes.add(c);
                }
                return listaClientes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener un empleado por su ID
    public Optional<Cliente> obtenerPorId(int id) {
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "SELECT id_cliente, nombre, edad, id_venta FROM Cliente WHERE id_cliente = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            rs.getInt("id_venta")
                    );
                    return Optional.of(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Método para mostrar todos los empleados
    public void mostrarClientes() {
        try (Connection con = getConexion()) {
            if (con != null) {
                obtenerCliente();
                for (Cliente cliente : listaClientes) {
                    System.out.println(cliente);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos de un empleado
    public boolean actualizarCliente(Cliente cliente) {
        boolean exito = false;
        String sql = "UPDATE Cliente SET nombre = ?, edad = ?, id_venta = ? WHERE id_cliente = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, cliente.getNombre());
                    ps.setInt(2, cliente.getEdad());
                    ps.setInt(3, cliente.getId_venta());

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
    public boolean eliminarCliente(int idCliente) {
        boolean exito = false;
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idCliente);
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