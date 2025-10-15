import java.util.Scanner;

public class Menu {
    Libro libro = new Libro();
    Cliente cliente = new Cliente();
    static Scanner sc = new Scanner(System.in);

    public void menuLibro() {
        libro.crearArchivosLibro();

        while (true) {
            System.out.println("----------------Menu Libro-----------------");
            System.out.println("1. Añadir libros nuevos cuando me llegan pedidos de la distribuidora");
            System.out.println("2. Ver el listado completo de todos mis libros");
            System.out.println("3. Buscar un libro específico por su ISBN para ver si lo tengo");
            System.out.println("4. Buscar todos los libros de una categoría concreta por ejemplo, todos los de Novela");
            System.out.println("5. Actualizar el stock cuando recibo más unidades de un libro");
            System.out.println("6. Ver qué libros están por debajo de 5 unidades (mi nivel de alerta para hacer pedidos)");
            System.out.println("7. Salir al menu principal");
            System.out.println("Elige un opcion del menu: ");
            int eleccion = sc.nextInt();
            sc.nextLine();
            switch (eleccion) {
                case 1:
                    libro.nuevoLibro();
                    break;
                case 2:
                    libro.mostrarLibros();
                    break;
                case 3:
                    libro.buscarLibroISBN();
                    break;
                case 4:
                    libro.buscarPorCategoria();
                    break;
                case 5:
                    libro.actualizarStock();
                    break;
                case 6:
                    libro.avisoStockMenor5();
                    break;
                case 7:
                    System.exit(0);
                    break;

            }

        }

    }

    public void menuCliente() {
        cliente.crearArchivoCliente();
        while (true) {
            System.out.println("----------------Menu Cliente-----------------");
            System.out.println("1. Dar de alta a nuevos clientes cuando vienen por primera vez");
            System.out.println("2. Ver el listado de todos mis clientes");
            System.out.println("3. Buscar un cliente por su DNI");
            System.out.println("4. Ver quiénes son mis 5 mejores clientes (los que más han comprado)");
            System.out.println("5. Poder actualizar el teléfono o email si un cliente me dice que ha cambiado");
            System.out.println("6. Salir al menu principal");
            System.out.println("Elige un opcion del menu: ");
            int eleccion = sc.nextInt();
            sc.nextLine();

            switch (eleccion) {
                case 1:
                    cliente.nuevoCliente();
                    break;
                case 2:
                    cliente.verClientes();
                    break;
                case 3:
                    cliente.buscarPorDNI();
                    break;
                case 4:
                    cliente.ver5MejoresClientes();
                    break;
                case 5:
                    cliente.actualizrCliente();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }

        }
        }
    }
