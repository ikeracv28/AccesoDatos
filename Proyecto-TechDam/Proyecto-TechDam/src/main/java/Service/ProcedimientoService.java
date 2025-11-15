package Service;

import java.sql.*;

import static Conexion.ConexionHikari.getConexion;

public class ProcedimientoService {

    // Método para llamar al procedimiento `actualizar_salario_departamento`
    public int actualizarSalarioDepartamento(String departamento, double porcentaje) {
        int empleadosActualizados = 0;
        String sql = "{CALL actualizar_salario_departamento(?, ?, ?)}"; // Llamada al procedimiento almacenado con parámetros IN y OUT

        try (Connection con = getConexion()) {
            if (con != null) {
                try (CallableStatement cs = con.prepareCall(sql)) {
                    // Establecemos los parámetros de entrada
                    cs.setString(1, departamento);
                    cs.setDouble(2, porcentaje);

                    // Registramos el parámetro de salida
                    cs.registerOutParameter(3, Types.INTEGER);

                    // Ejecutamos el procedimiento
                    cs.execute();

                    // Obtenemos el valor del parámetro de salida
                    empleadosActualizados = cs.getInt(3);

                    System.out.println("Empleados actualizados: " + empleadosActualizados);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleadosActualizados;
    }

    // Método para llamar al procedimiento `transferir_presupuesto`
    public boolean transferirPresupuesto(int proyectoOrigen, int proyectoDestino, double monto) {
        boolean exito = false;
        String sql = "{CALL transferir_presupuesto(?, ?, ?, ?)}"; // Llamada al procedimiento almacenado con parámetros IN y OUT

        try (Connection con = getConexion()) {
            if (con != null) {
                try (CallableStatement cs = con.prepareCall(sql)) {
                    // Establecemos los parámetros de entrada
                    cs.setInt(1, proyectoOrigen);
                    cs.setInt(2, proyectoDestino);
                    cs.setDouble(3, monto);

                    // Registramos el parámetro de salida
                    cs.registerOutParameter(4, Types.BOOLEAN);

                    // Ejecutamos el procedimiento
                    cs.execute();

                    // Obtenemos el valor del parámetro de salida
                    exito = cs.getBoolean(4);

                    System.out.println("Transferencia de presupuesto exitosa: " + exito);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
}
