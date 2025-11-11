import DAO.ConsultasCrud;

import java.util.Scanner;


public class Menu {
    public void Menu(){
        Scanner sc = new Scanner(System.in);
        ConsultasCrud cons = new ConsultasCrud();


        while(true){
            System.out.println("1. Insertar Proyectos");
            System.out.println("2. Actuelizar Proyectos");
            System.out.println("3. Eliminar Proyectos");
            System.out.println("4. Listar Proyectos");
            System.out.println("5. Salir");

            System.out.println("Ingrese el numero de la opcion que desea: ");
            int opcion = sc.nextInt();
            switch (opcion){
                case 1:
                    cons.insertarProyectos();
                    break;
                case 2:
                    cons.actualizarProyectos();
                    break;
                case 3:
                    cons.eliminarProyectos();
                    break;
                case 4:
                    cons.mostrarProyectos();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}
