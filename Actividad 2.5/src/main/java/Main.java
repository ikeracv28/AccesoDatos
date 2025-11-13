import Conexion.ConexionHikari;
import DAO.PruebaCallable;

public class Main {
    public static void main(String[] args) {
        try{
            PruebaCallable prueba = new PruebaCallable();

            prueba.llamarProcedimiento();
        } finally {
            ConexionHikari.cerrarPool();
        }



    }
}
