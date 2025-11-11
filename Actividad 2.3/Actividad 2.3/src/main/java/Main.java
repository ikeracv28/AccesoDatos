import Conexion.ConexionHikari;
import DAO.ConsultasCrud;

public class Main {
    public static void main(String[] args) {
        ConsultasCrud consultas = new ConsultasCrud();
        Menu menu = new Menu();

        try{
            menu.Menu();
        } finally {
            // ✅ Aquí sí se cierra el pool UNA sola vez
            ConexionHikari.cerrarPool();
        }


    }

}
