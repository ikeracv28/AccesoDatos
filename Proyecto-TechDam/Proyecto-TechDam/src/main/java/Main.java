import DAO.EmpleadoDAO;
import DAO.ProyectoDAO;

public class Main {
    public static void main(String[] args) {
        ProyectoDAO proyectoDAO = new ProyectoDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        proyectoDAO.mostrarProyectos();
        empleadoDAO.mostrarEmpleados();
        System.out.println(proyectoDAO.obtenerPorId(1));


    }
}
