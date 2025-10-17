import java.util.Scanner;

public class Menu {
    Libro libro = new Libro();
    Cliente cliente = new Cliente();
    Ventas ventas = new Ventas();
    static Scanner sc = new Scanner(System.in);

    public void menuGeneral() {
        libro.crearArchivosLibro();
        cliente.crearArchivoCliente();
        ventas.crearArchivos();

        while (true) {
            System.out.println("----------------Menu General-----------------");
            System.out.println("1. Ir al menu de Clientes");
            System.out.println("2. Ir al menu de Libros");
            System.out.println("3. Ir al menu de Ventas");
            System.out.println("4. Salir");
            System.out.println("Elige un opcion del menu: ");
            int eleccion = sc.nextInt();
            sc.nextLine();
            switch (eleccion) {
                case 1:
                    menuCliente();
                    break;
                case 2:
                    menuLibro();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Numero no valido");
            }

        }

    }




    public void menuLibro() {


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
                    menuGeneral();
                    break;
                default:
                    System.out.println("Numero no valido");

            }

        }

    }

    public void menuCliente() {

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
                    menuGeneral();
                    break;
                default:
                    System.out.println("Numero no valido");
            }

        }
        }

        public void menuVentas(){

            while (true) {
                System.out.println("----------------Menu Ventas-----------------");
                System.out.println("1. Registrar una nueva venta (esto debe restar automáticamente del stock y sumar al contador de compras del cliente)");
                System.out.println("2. Ver todas las ventas realizadas en una fecha concreta");
                System.out.println("3. Ver todas las ventas de un cliente específico (por su DNI)");
                System.out.println("4. Ver todas las ventas de un libro específico (por ISBN)");
                System.out.println("5. Calcular el total de dinero ganado en todas las ventas");
                System.out.println("6. Salir al menu principal");
                System.out.println("Elige un opcion del menu: ");
                int eleccion = sc.nextInt();
                sc.nextLine();

                switch (eleccion) {
                    case 1:
                      ventas.nuevaVenta();
                        break;
                    case 2:
                        ventas.buscarPorFecha();
                        break;
                    case 3:
                        ventas.buscarPorClientes();
                        break;
                    case 4:
                        ventas.buscarPorISBN();
                        break;
                    case 5:
                       ventas.calcularTotalTodasVentas();
                        break;
                    case 6:
                        menuGeneral();
                        break;
                    default:
                        System.out.println("Numero no valido");
                }

            }
        }


    }
