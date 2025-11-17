import DAO.EmpleadoDAO;
import DAO.ProyectoDAO;
import Modelo.Empleado;
import Modelo.Proyecto;
import Service.ProcedimientoService;
import Service.TransaccionesService;


import java.math.BigDecimal;
import java.util.Scanner;

import static utils.Utils.leerDouble;
import static utils.Utils.leerEntero;

public class Menu {

    private ProyectoDAO proyectoDAO;
    private EmpleadoDAO empleadoDAO;
    private TransaccionesService transaccionesService;
    private ProcedimientoService procedimientoService;
    private Scanner sc;
    utils.Utils utils;


    // Constructor para inicializar las dependencias
    public Menu() {
        proyectoDAO = new ProyectoDAO();
        empleadoDAO = new EmpleadoDAO();
        transaccionesService = new TransaccionesService();
        procedimientoService = new ProcedimientoService();
        sc = new Scanner(System.in);
    }

    // Método que ejecuta el menú interactivo
    public void menu() {
        int opcion;

        while (true) {
            // Menú principal
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Menú Empleados");
            System.out.println("2. Menú Proyectos");
            System.out.println("3. Transacciones y Procedimientos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();


            switch (opcion) {
                case 1:
                    // Llamar al submenú de empleados
                    menuEmpleados();
                    break;

                case 2:
                    // Llamar al submenú de proyectos
                    menuProyectos();
                    break;

                case 3:
                    // Llamar al submenú de operaciones administrativas
                    menuOperacionesAdministrativas();
                    break;

                case 4:
                    // Salir
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Submenú para Operaciones Administrativas (Procedimientos y Funciones)
    public void menuOperacionesAdministrativas() {
        int opcion;

        while (true) {
            // Menú de opciones de operaciones administrativas
            System.out.println("\n=== Menú de Procedimientos y Funciones ===");
            System.out.println("1. Actualizar Salarios por Departamento");
            System.out.println("2. Transferir Presupuesto entre Proyectos");
            System.out.println("3. Asignar Empleados a Proyectos");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();


            switch (opcion) {
                case 1:
                    // Actualizar salarios por departamento
                    actualizarSalariosPorDepartamento();
                    break;

                case 2:
                    // Transferir presupuesto entre proyectos
                    realizarTransferenciaPresupuesto();
                    break;

                case 3:
                    // Asignar empleados a proyectos
                    asignarEmpleadosAProyectos();
                    break;

                case 4:
                    // Volver al Menú Principal
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Método corregido para actualizar los salarios por departamento
    public void actualizarSalariosPorDepartamento() {
        System.out.print("Ingrese el nombre del departamento: ");
        String departamento = sc.next(); // Capturamos el nombre del departamento
        System.out.print("Ingrese el porcentaje de incremento: ");
        double porcentaje = leerDouble(); // Capturamos el porcentaje

        // Llamamos al servicio con los parámetros correctos
        int empleadosActualizados = procedimientoService.actualizarSalarioDepartamento(departamento, porcentaje);
        System.out.println(empleadosActualizados + " empleados actualizados.");
        empleadoDAO.mostrarEmpleados();
    }

    // Método para asignar empleados a proyectos
    public void asignarEmpleadosAProyectos() {
        System.out.print("Ingrese el número de empleados que desea asignar: ");
        int numEmpleados = leerEntero();
        int[] empleados = new int[numEmpleados];

        // Pedimos los IDs de los empleados
        for (int i = 0; i < numEmpleados; i++) {
            System.out.print("Ingrese el ID del empleado " + (i + 1) + ": ");
            empleados[i] = leerEntero();
        }

        System.out.print("Ingrese el número de proyectos a asignar: ");
        int numProyectos = sc.nextInt();
        int[] proyectos = new int[numProyectos];

        // Pedimos los IDs de los proyectos
        for (int i = 0; i < numProyectos; i++) {
            System.out.print("Ingrese el ID del proyecto " + (i + 1) + ": ");
            proyectos[i] = leerEntero();
        }

        // Llamamos al servicio para asignar empleados a proyectos
        transaccionesService.asignarEmpleadosAProyectos(empleados, proyectos);
    }

    // Método para realizar la transferencia de presupuesto
    public void realizarTransferenciaPresupuesto() {
        System.out.print("Ingrese el ID del proyecto de origen: ");
        int proyectoOrigen = leerEntero();
        System.out.print("Ingrese el ID del proyecto de destino: ");
        int proyectoDestino = leerEntero();
        System.out.print("Ingrese el monto a transferir: ");
        double monto = leerDouble();
        transaccionesService.transferirPresupuestoEntreProyectos(proyectoOrigen, proyectoDestino, monto);
        proyectoDAO.mostrarProyectos();
    }

    // Submenú para Empleados
    public void menuEmpleados() {
        int opcion;

        while (true) {
            System.out.println("\n=== Menú Empleados ===");
            System.out.println("1. Mostrar todos los Empleados");
            System.out.println("2. Crear Empleado");
            System.out.println("3. Actualizar Empleado");
            System.out.println("4. Eliminar Empleado");
            System.out.println("5. Obtener Empleado por ID");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    // Mostrar todos los empleados
                    empleadoDAO.mostrarEmpleados();
                    break;

                case 2:
                    // Crear un nuevo empleado
                    Empleado nuevoEmpleado = crearEmpleado();
                    empleadoDAO.crearEmpleado(nuevoEmpleado);
                    break;

                case 3:
                    // Actualizar empleado
                    actualizarEmpleado();
                    break;

                case 4:
                    // Eliminar empleado
                    eliminarEmpleado();
                    break;

                case 5:
                    // Obtener empleado por ID
                    obtenerEmpleadoPorId();
                    break;

                case 6:
                    // Volver al Menú Principal
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Submenú para Proyectos
    public void menuProyectos() {
        int opcion;

        while (true) {
            System.out.println("\n=== Menú Proyectos ===");
            System.out.println("1. Mostrar todos los Proyectos");
            System.out.println("2. Crear Proyecto");
            System.out.println("3. Actualizar Proyecto");
            System.out.println("4. Eliminar Proyecto");
            System.out.println("5. Obtener Proyecto por ID");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();


            switch (opcion) {
                case 1:
                    proyectoDAO.mostrarProyectos();
                    break;

                case 2:
                    Proyecto nuevoProyecto = crearProyecto();
                    proyectoDAO.crearProyectos(nuevoProyecto);
                    break;

                case 3:
                    actualizarProyecto();
                    break;

                case 4:
                    eliminarProyecto();
                    break;

                case 5:
                    obtenerProyectoPorId();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Método para pedir los datos del proyecto y devolver el objeto Proyecto
    public Proyecto crearProyecto() {
        System.out.print("Ingrese el nombre del proyecto: ");
        String nombreProyecto = sc.nextLine();
        System.out.print("Ingrese el presupuesto del proyecto: ");
        double presupuesto = leerDouble();


        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setNombre(nombreProyecto);
        nuevoProyecto.setPresupuesto(BigDecimal.valueOf(presupuesto));

        return nuevoProyecto;
    }

    // Método para pedir los datos del empleado y devolver el objeto Empleado
    public Empleado crearEmpleado() {
        System.out.print("Ingrese el nombre del empleado: ");
        String nombreEmpleado = sc.nextLine();
        System.out.print("Ingrese el departamento del empleado: ");
        String departamento = sc.nextLine();
        System.out.print("Ingrese el salario del empleado: ");
        double salario = leerDouble();

        boolean activo = false;
        boolean entradaValida = false;

        // Validación de "activo"
        while (!entradaValida) {
            System.out.print("Está activo el empleado? (true/false): ");
            if (sc.hasNextBoolean()) {
                activo = sc.nextBoolean();
                entradaValida = true;
            } else {
                System.out.println("Entrada inválida. Por favor ingrese 'true' o 'false'.");
                sc.nextLine(); // Limpiar el buffer
            }
        }

        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setNombre(nombreEmpleado);
        nuevoEmpleado.setDepartamento(departamento);
        nuevoEmpleado.setSalario(BigDecimal.valueOf(salario));
        nuevoEmpleado.setActivo(activo);

        return nuevoEmpleado;
    }

    // Método para actualizar un empleado
    public void actualizarEmpleado() {
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int idEmpleado = leerEntero();
        Empleado empleado = empleadoDAO.obtenerPorId(idEmpleado).orElse(null);
        if (empleado != null) {
            // Solicitar los nuevos datos del empleado y actualizar
            System.out.print("Ingrese el nuevo nombre del empleado: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese el nuevo departamento del empleado: ");
            String departamento = sc.nextLine();
            System.out.print("Ingrese el nuevo salario del empleado: ");
            double salario = leerDouble();
            System.out.print("Está activo el empleado? (true/false): ");
            boolean activo = sc.nextBoolean();
            sc.nextLine();

            empleado.setNombre(nombre);
            empleado.setDepartamento(departamento);
            empleado.setSalario(BigDecimal.valueOf(salario));
            empleado.setActivo(activo);

            empleadoDAO.actualizarEmpleado(empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    // Método para eliminar un empleado
    public void eliminarEmpleado() {
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int idEmpleado = leerEntero();
        if (empleadoDAO.eliminarEmpleado(idEmpleado)) {
            System.out.println("Empleado eliminado.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    // Método para obtener un empleado por ID
    public void obtenerEmpleadoPorId() {
        System.out.print("Ingrese el ID del empleado a obtener: ");
        int idEmpleado = leerEntero();
        Empleado empleado = empleadoDAO.obtenerPorId(idEmpleado).orElse(null);
        if (empleado != null) {
            System.out.println(empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    // Método para actualizar un proyecto
    public void actualizarProyecto() {
        System.out.print("Ingrese el ID del proyecto a actualizar: ");
        int idProyecto = leerEntero();
        Proyecto proyecto = proyectoDAO.obtenerPorId(idProyecto).orElse(null);
        if (proyecto != null) {
            // Solicitar los nuevos datos del proyecto y actualizar
            System.out.print("Ingrese el nuevo nombre del proyecto: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese el nuevo presupuesto del proyecto: ");
            double presupuesto = leerDouble();

            proyecto.setNombre(nombre);
            proyecto.setPresupuesto(BigDecimal.valueOf(presupuesto));

            proyectoDAO.actualizarProyecto(proyecto);
            System.out.println("Proyecto actualizado.");
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }

    // Método para eliminar un proyecto
    public void eliminarProyecto() {
        System.out.print("Ingrese el ID del proyecto a eliminar: ");
        int idProyecto = leerEntero();
        if (proyectoDAO.eliminarProyecto(idProyecto)) {
            System.out.println("Proyecto eliminado.");
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }


    // Método para obtener un proyecto por ID
    public void obtenerProyectoPorId() {
        System.out.print("Ingrese el ID del proyecto a obtener: ");
        int idProyecto = leerEntero();
        Proyecto proyecto = proyectoDAO.obtenerPorId(idProyecto).orElse(null);
        if (proyecto != null) {
            System.out.println("Proyecto encontrado: " + proyecto);
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }

}
