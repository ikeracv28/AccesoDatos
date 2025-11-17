package DAO;

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

public class LibroDAO {

    Libro libro = new Libro();
    static Scanner sc = new Scanner(System.in);

    static List<Libro> listalibro = new ArrayList<>();

    // Método para crear un nuevo empleado
    public int crearLibro(Libro libro) {

        int idGenerado = -1; // Inicializamos el ID como un valor no válido
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "INSERT INTO Libro (titulo, autor, precio, stock, id_genero) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    // Seteamos los valores
                    ps.setString(1, libro.getTitulo());
                    ps.setString(2, libro.getAutor());
                    ps.setDouble(3, libro.getPrecio());
                    ps.setInt(4, libro.getStock());
                    ps.setInt(5, libro.getId_genero());

                    // Ejecutamos la inserción
                    int filas = ps.executeUpdate();

//                    // Obtenemos el ID generado
//                    try (ResultSet rs = ps.getGeneratedKeys()) {
//                        if (rs.next()) {
//                            idGenerado = rs.getInt(1);  // Recuperamos el primer valor de la columna generada (ID)
//                        }
//                    }

                    System.out.println("Filas insertadas: " + filas);
                    // para que nos devuleva el ID del empleado creado

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

//    public void mostrarLibroSInArray(){
//        try (Connection con = getConexion()) {
//            if (con != null) {
//                Statement st = con.createStatement();
//                ResultSet rs = st.executeQuery("SELECT * FROM Libro");
//
//                System.out.println("\n=== Libros ===");
//                while (rs.next()) {
//                    System.out.printf("ID: %d | Titulo: %s | Autor: %s  | Precio: %.2f €%n | Stock: %d | ID_categoria: %d |",
//                            rs.getInt("id_libro"), rs.getString("titulo"), rs.getString("autor"), rs.getDouble("precio"), rs.getInt("stock"),rs.getInt("id_genero") );
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // Método para obtener todos los empleados
    public List<Libro> obtenerLibros() {
        try (Connection con = getConexion()) {
            if (con != null) {
                listalibro.clear();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Libro");

                System.out.println("\n=== Libros ===");
                while (rs.next()) {
                    Libro l = new Libro(
                            rs.getInt("id_libro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getInt("id_genero")
                    );
                    listalibro.add(l);
                }

                return listalibro;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener un empleado por su ID
    public Optional<Libro> obtenerPorId(int id) {
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "SELECT id_libro, titulo, autor, precio, stock, id_genero FROM Libro WHERE id_libro = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Libro l = new Libro(
                            rs.getInt("id_libro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getInt("id_genero")
                    );
                    return Optional.of(l);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Método para mostrar todos los empleados
    public void mostrarLibros() {
        try (Connection con = getConexion()) {
            if (con != null) {
                obtenerLibros();
                for (Libro libro : listalibro) {
                    System.out.println(libro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos de un empleado
    public boolean actualizarLibro(Libro libro) {
        boolean exito = false;
        String sql = "UPDATE Libro SET titulo = ?, autor = ?, precio = ?, stock = ?, id_genero = ? WHERE id_libro = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, libro.getTitulo());
                    ps.setString(2, libro.getAutor());
                    ps.setDouble(3, libro.getPrecio());
                    ps.setInt(4, libro.getStock());
                    ps.setInt(5, libro.getId_genero());
                    ps.setInt(6, libro.getId_libro());

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
    public boolean eliminarLibro(int idLibro) {
        boolean exito = false;
        String sql = "DELETE FROM Libro WHERE id_libro = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idLibro);
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
