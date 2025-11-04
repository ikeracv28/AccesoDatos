package Conexion;

public class Main {
    public static void main(String[] args) {
        ConsultasSQL cons = new ConsultasSQL();
        cons.mostrarEmpleados();
        cons.mostrarEmpleadoPorID();
        cons.llamarAProcedimiento();
    }
}
