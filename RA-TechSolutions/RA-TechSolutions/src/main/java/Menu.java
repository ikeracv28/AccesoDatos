import DAO.LibroDAO;
import Modelo.Libro;

import java.math.BigDecimal;
import java.util.Scanner;

import static Utils.Utils.leerDouble;
import static Utils.Utils.leerEntero;

public class Menu {

    private Scanner sc;
    private LibroDAO libroDAO;

    // Constructor para inicializar las dependencias
    public Menu() {
        libroDAO = new LibroDAO();

        sc = new Scanner(System.in);
    }


    // Submenú para Empleados
    public void menuLibro() {
        int opcion;

        while (true) {
            System.out.println("\n=== Menú Libro ===");
            System.out.println("1. Mostrar todos los libros");
            System.out.println("2. Crear libro");
            System.out.println("3. Actualizar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Obtener libro por ID");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    // Mostrar todos los empleados
                    libroDAO.mostrarLibros();
                    break;

                case 2:
                    // Crear un nuevo empleado
                    Libro nuevoLibro = crearLibro();
                    libroDAO.crearLibro(nuevoLibro);
                    break;

                case 3:
                    // Actualizar empleado
                    actualizarLibro();
                    break;

                case 4:
                    // Eliminar empleado
                    eliminarLibro();
                    break;

                case 5:
                    // Obtener empleado por ID
                    obtenerLibroPorId();
                    break;

                case 6:
                    // Volver al Menú Principal
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }


    // Método para pedir los datos del empleado y devolver el objeto Empleado
    public Libro crearLibro() {
        System.out.print("Ingrese el Titulo del libro: ");
        String nombreTitulo = sc.nextLine();
        System.out.print("Ingrese el Autor del libro: ");
        String autor = sc.nextLine();
        System.out.print("Ingrese el Precio del libro: ");
        double precio = leerDouble();
        System.out.print("Ingrese el Stock del libro: ");
        int stock = leerEntero();
        System.out.print("Ingrese el id_genero del libro: ");
        int id_genero = leerEntero();

        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo(nombreTitulo);
        nuevoLibro.setAutor(autor);
        nuevoLibro.setPrecio(precio);
        nuevoLibro.setStock(stock);
        nuevoLibro.setId_genero(id_genero);


        return nuevoLibro;
    }

    // Método para actualizar un empleado
    public void actualizarLibro() {
        System.out.print("Ingrese el ID del libro a actualizar: ");
        int idLibro = leerEntero();
        Libro libro = libroDAO.obtenerPorId(idLibro).orElse(null);
        if (libro != null) {
            // Solicitar los nuevos datos del empleado y actualizar
            System.out.print("Ingrese el nuevo titulo del libro: ");
            String titulo = sc.nextLine();
            System.out.print("Ingrese el nuevo autor del libro: ");
            String autor = sc.nextLine();
            System.out.print("Ingrese el nuevo precio del libro: ");
            double precio = leerDouble();
            System.out.print("Stock del libro: ");
            int stock = sc.nextInt();
            System.out.print("ID del genero del libro: ");
            int id_genero = sc.nextInt();

            sc.nextLine();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setPrecio(precio);
            libro.setStock(stock);
            libro.setId_genero(id_genero);


            libroDAO.actualizarLibro(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    // Método para eliminar un empleado
    public void eliminarLibro() {
        System.out.print("Ingrese el ID del libro a eliminar: ");
        int idLibro = leerEntero();
        if (libroDAO.eliminarLibro(idLibro)) {
            System.out.println("Libro eliminado.");
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    // Método para obtener un empleado por ID
    public void obtenerLibroPorId() {
        System.out.print("Ingrese el ID del libro a obtener: ");
        int idLibro = leerEntero();
        Libro libro = libroDAO.obtenerPorId(idLibro).orElse(null);
        if (libro != null) {
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }


}
