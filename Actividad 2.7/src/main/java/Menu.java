import DAO.AsignacionesDAO;
import DAO.EmpleadosDAO;
import DAO.ProyectosDAO;

import java.util.Scanner;

public class Menu {

        public void Menu(){
            Scanner sc = new Scanner(System.in);
            AsignacionesDAO asignacionesDAO = new AsignacionesDAO();
            EmpleadosDAO empleadosDAO = new EmpleadosDAO();
            ProyectosDAO proyectosDAO = new ProyectosDAO();

            while(true){
                System.out.println("1. Insertar Proyectos");
                System.out.println("2. Insertar Empleados");
                System.out.println("3. Asignar proyectos a empleados");
                System.out.println("4. Realizar transaccion");
                System.out.println("5. Mostrar tablas ");
                System.out.println("6. Salir");
                System.out.println("Ingrese el numero de la opcion que desea: ");

                int opcion = sc.nextInt();
                switch (opcion){
                    case 1:
                        proyectosDAO.insertarProyectos();
                        break;
                    case 2:
                        empleadosDAO.insertarProyectos();
                        break;
                    case 3:
                        asignacionesDAO.llamarProcedimiento();
                        break;
                    case 4:
                        empleadosDAO.hacerTransferencia();
                        break;
                    case 5:
                        asignacionesDAO.mostrarAsignaciones();
                        empleadosDAO.mostrarEmpleados();
                        proyectosDAO.mostrarProyectos();
                        break;
                    case 6:
                        System.exit(0);

                }
            }
        }
    }
