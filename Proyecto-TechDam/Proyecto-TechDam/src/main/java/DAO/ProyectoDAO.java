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


    public int crearProyectos(Proyecto proyecto) {

        int idGenerado = -1; // Inicializamos el ID como un valor no válido
        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "INSERT INTO proyectos (nombre, presupuesto) VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    // Seteamos los valores
                    ps.setString(1, proyecto.getNombre());
                    ps.setBigDecimal(2, proyecto.getPresupuesto());

                    // Ejecutamos la inserción
                    int filas = ps.executeUpdate();

                    // Obtenemos el ID generado
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            idGenerado = rs.getInt(1);  // Recuperamos el primer valor de la columna generada (ID)
                        }
                    }

                    System.out.println("Filas insertadas: " + filas);
                    System.out.println("ID del proyecto creado: " + idGenerado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }


    public List<Proyecto> obtenerProyectos(){
        try (Connection con = getConexion()) {
            if (con != null) {
                listaProyecto.clear();
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

        try (Connection con = getConexion()) {
            if (con != null) {
                String sql = "SELECT id_proyecto, nombre, presupuesto FROM proyectos WHERE id_proyecto = ?";
                PreparedStatement ps = con.prepareStatement(sql);
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
                //return Optional.empty();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
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


    public boolean actualizarProyecto(Proyecto proyecto) {
        boolean exito = false;
        String sql = "UPDATE proyectos SET nombre = ?, presupuesto = ? WHERE id_proyecto = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, proyecto.getNombre());
                    ps.setBigDecimal(2, proyecto.getPresupuesto());
                    ps.setInt(3, proyecto.getIdProyecto());

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



    public boolean eliminarProyecto(int idProyecto) {
        boolean exito = false;
        String sql = "DELETE FROM proyectos WHERE id_proyecto = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idProyecto);
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
