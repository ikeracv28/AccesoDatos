package com.example.demo.Controller;

import com.example.demo.Models.Empleado;
import com.example.demo.Models.Tarea;
import com.example.demo.Service.EmpleadoService;
import com.example.demo.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Controller implements CommandLineRunner {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private TareaService tareaService;
    static Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("1. Mostrar empleados por ID");
            System.out.println("2. Mostrar horas");
            System.out.println("3. Registrar empleado");

            System.out.println("Elige una opcion del menu");
            int eleccion = scanner.nextInt();

            switch (eleccion) {
                case 1:
                    mostrarEmpleadoPorId();
                    break;
                case 2:
                    consultarHorasPorEmpleado();
                    break;
                case 3:
                    registrarMasHorasTareas();
                    break;

                case 4:
                    System.out.println("¡A entrenar! Adiós.");
                    return; // Sale del programa (pero el servidor sigue activo)
                default:
                    System.out.println("Opción no válida.");
            }

        }

    }

    public void mostrarEmpleadoPorId(){
        try{
            System.out.println("Dime el id que quieres buscar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Optional<Empleado> empleado = empleadoService.buscarEmpleadoPorId(id);
            if (empleado.isEmpty()){
                System.out.println("Error, empleado vacio");
            } else if (empleado.isPresent()) {
                Empleado empleado1 = empleado.get();
                System.out.println("Empleado: ID: " + empleado1.getId() + " | Nombre: " + empleado1.getNombre() + "| Departamento: " + empleado1.getDepartamento());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void consultarHorasPorEmpleado(){
        System.out.println("Dime el id que quieres buscar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        double horas = tareaService.horasPorEmpleado(id);
        System.out.println("El empleado con id " + id + "ha hecho " + horas + " horas");
    }

    public void registrarMasHorasTareas(){
        Tarea tarea;
        System.out.println("Dime el id del empleado que quieras asignar una tarea: ");
        int idEmpleado = scanner.nextInt();
        scanner.nextLine();
        System.out.println("A que proyecto");
        int idProyecto = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Cuantas horas ha trabajado");
        double horas = scanner.nextDouble();
        scanner.nextLine();
        tarea = new Tarea(idEmpleado, idProyecto, horas);
        try{
            if (tareaService.insertarTareas(tarea)){
                System.out.println("tarea registrada con exito");
            } else {
                System.out.println("error al registrar la tarea");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
