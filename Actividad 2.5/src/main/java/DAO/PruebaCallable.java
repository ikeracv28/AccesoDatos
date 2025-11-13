package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import static Conexion.ConexionHikari.getConexion;

public class PruebaCallable {
    public void llamarProcedimiento(){

        try (Connection con = getConexion()) {
            if (con != null) {
                CallableStatement cs = con.prepareCall("{call incrementar_salario(?, ?, ?)}");

                cs.setInt(1, 1);
                cs.setDouble(2, 500);// parámetro IN

                cs.registerOutParameter(3, Types.DOUBLE); // parámetro OUT


                cs.execute();

                double salario = cs.getDouble(3); // recuperar parámetro OUT
                System.out.println("El salario del empleado 1 es: " + salario);

                cs.close();

            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
