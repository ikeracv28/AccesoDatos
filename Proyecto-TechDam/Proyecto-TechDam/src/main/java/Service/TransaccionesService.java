package Service;

import java.sql.*;
import java.time.LocalDate;

import static Conexion.ConexionHikari.getConexion;

public class TransaccionesService {

    // Método para asignar múltiples empleados a múltiples proyectos utilizando transacciones
    public void asignarEmpleadosAProyectos(int[] empleados, int[] proyectos) {
        String sql = "{CALL asignarEmpleadoAProyecto(?, ?, ?)}"; // Llamada al procedimiento almacenado para asignar empleados

        try (Connection con = getConexion()) {
            if (con != null) {
                // Desactivar el auto-commit para manejar la transacción manualmente
                con.setAutoCommit(false);

                try (CallableStatement cs = con.prepareCall(sql)) {
                    // Asegurarse de que la cantidad de empleados y proyectos sea la misma
                    if (empleados.length != proyectos.length) {
                        System.out.println("El número de empleados no coincide con el número de proyectos.");
                        return;
                    }

                    // Iteramos por los empleados y proyectos
                    for (int i = 0; i < empleados.length; i++) {
                        // Establecemos los parámetros para cada asignación
                        cs.setInt(1, empleados[i]); // id_empleado
                        cs.setInt(2, proyectos[i]); // id_proyecto
                        cs.setDate(3, Date.valueOf(LocalDate.now())); // fecha_asignacion

                        // Ejecutamos la asignación
                        cs.execute();
                    }

                    // Si todas las asignaciones fueron exitosas, confirmamos la transacción
                    con.commit();
                    System.out.println("Todos los empleados han sido asignados correctamente a los proyectos.");

                } catch (SQLException e) {
                    // Si algo falla, revertimos todas las asignaciones
                    con.rollback();
                    System.out.println("Error en la asignación. Transacción revertida.");
                    e.printStackTrace();
                } finally {
                    // Restauramos el auto-commit
                    con.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para transferir presupuesto entre proyectos utilizando una transacción
    public void transferirPresupuestoEntreProyectos(int proyectoOrigen, int proyectoDestino, double monto) {
        String sqlSelect = "SELECT presupuesto FROM proyectos WHERE id_proyecto = ?";
        String sqlRestar = "UPDATE proyectos SET presupuesto = presupuesto - ? WHERE id_proyecto = ?";
        String sqlSumar = "UPDATE proyectos SET presupuesto = presupuesto + ? WHERE id_proyecto = ?";

        try (Connection con = getConexion()) {
            if (con != null) {
                // Desactivar auto-commit para manejar la transacción manualmente
                con.setAutoCommit(false);

                try (PreparedStatement psSelect = con.prepareStatement(sqlSelect);
                     PreparedStatement psRestar = con.prepareStatement(sqlRestar);
                     PreparedStatement psSumar = con.prepareStatement(sqlSumar)) {

                    // Consultar presupuesto del proyecto de origen
                    psSelect.setInt(1, proyectoOrigen);
                    ResultSet rs = psSelect.executeQuery();

                    if (!rs.next()) {
                        System.out.println("Proyecto de origen no encontrado.");
                        con.rollback();
                        return;
                    }

                    double presupuestoOrigen = rs.getDouble("presupuesto");

                    // Comprobar si tiene suficiente dinero
                    if (presupuestoOrigen < monto) {
                        System.out.println("No hay suficiente presupuesto en el proyecto de origen.");
                        con.rollback();
                        return;
                    }

                    // Restar presupuesto del proyecto de origen
                    psRestar.setDouble(1, monto);
                    psRestar.setInt(2, proyectoOrigen);
                    int filasRestadas = psRestar.executeUpdate();

                    // Sumar presupuesto al proyecto de destino
                    psSumar.setDouble(1, monto);
                    psSumar.setInt(2, proyectoDestino);
                    int filasSumadas = psSumar.executeUpdate();

                    // Verificamos si ambas operaciones fueron exitosas
                    if (filasRestadas > 0 && filasSumadas > 0) {
                        // Si ambas operaciones fueron exitosas, confirmamos la transacción
                        con.commit();
                        System.out.println("Transferencia de presupuesto exitosa.");

                    } else {
                        // Si alguna operación falla, revertimos toda la transacción
                        con.rollback();
                        System.out.println("Error en la transferencia de presupuesto. Transacción revertida.");
                    }
                } catch (SQLException e) {
                    // Si ocurre una excepción, revertimos la transacción
                    con.rollback();
                    System.out.println("Error en la transferencia de presupuesto. Transacción revertida.");
                    e.printStackTrace();
                } finally {
                    // Restaurar auto-commit para futuras operaciones
                    con.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

