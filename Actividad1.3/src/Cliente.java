import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private String DNI;
    private String nombre;
    private String email;
    private int telefono;
    private int cantidad;
    static Scanner sc = new Scanner(System.in);

    public Cliente(String DNI, String nombre, String email, int telefono, int cantidad) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.cantidad = cantidad;
    }
    public Cliente(){}

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void crearArchivoCliente(){
        try{
            File f = new File("datos_librerias");
            if(!f.exists()){
                f.mkdir();
            }

            File fClientes = new File("datos_librerias/clientes.txt");
            if(!fClientes.exists()){
                fClientes.createNewFile();
            }



        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean validarDNIcliente(String DNI){
        try(BufferedReader br = new BufferedReader( new FileReader("datos_librerias/clientes.txt") )){
            String linea;
            while((linea = br.readLine()) != null){
                String[] partes =  linea.split(";");
                if(partes.length == 5){
                    System.out.println("Comparando: " + partes[0] + " con " + DNI);
                    if(partes[0].trim().equalsIgnoreCase(DNI.trim())){
                        return false;
                    }
                }
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public void guardarClientes(Cliente cliente){
        try(BufferedWriter bwClientes = new BufferedWriter(new FileWriter("datos_librerias/clientes.txt", true))){
            if(validarDNIcliente(cliente.getDNI())){
                String linea = (cliente.getDNI() +  ";" + cliente.getNombre() + ";" + cliente.getEmail() + ";" + cliente.getTelefono() + ";" + cliente.getCantidad());
                bwClientes.write(linea);
            }else {
                System.out.println("EL DNI ya lo tenemos registrado");
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void nuevoCliente(){
        try(BufferedWriter bwCliente = new BufferedWriter(new FileWriter("datos_librerias/clientes.txt"))){
            System.out.println("Dime el DNI del cliente que quieras añadir: ");
            String DNI = sc.nextLine();
            if(validarDNIcliente(DNI)){
                System.out.println("Dime el nombre del cliente: ");
                String nombre = sc.nextLine();
                System.out.println("Dime el correo del cliente: ");
                String email = sc.nextLine();
                System.out.println("Dime el telefono del cliente: ");
                int telefono = sc.nextInt();
                sc.nextLine();
                int cantidad = 0;
                guardarClientes(new Cliente(DNI, nombre, email, telefono, cantidad));
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void verClientes(){
        try(BufferedReader brClientes = new BufferedReader(new FileReader("datos_librerias/clientes.txt"))){
            String linea;
            while((linea = brClientes.readLine()) != null){
                String[] partes = linea.split(";");
                if(partes.length == 5){
                    System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4]);
                }else {
                    System.out.println("No hay mas clientes");
                }
            }

        }catch(IOException e ){
            System.out.println(e.getMessage());
        }
    }

    public void buscarPorDNI(){
        try(BufferedReader brCliente = new BufferedReader(new FileReader("datos_librerias/clientes.txt"))){
            System.out.println("Dime el DNI del cliente que quieres buscar: ");
            String DNI = sc.nextLine();
            if(validarDNIcliente(DNI)){
                System.out.println("Ese dni no lo tenemos registrado");
            }else {
                String linea;
                while ((linea = brCliente.readLine()) != null){
                    String[] partes = linea.split(";");
                    if (partes.length == 5){
                        if(partes[0].equalsIgnoreCase(DNI)){
                            System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4]);
                        }else {
                            System.out.println("Ese DNI no lo tenemos registrado.");
                        }
                    }
                }
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

   public void ver5MejoresClientes() {
        // Creamos una lista para almacenar los clientes leídos del archivo
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader brCliente = new BufferedReader(new FileReader("datos_librerias/clientes.txt"))) {
            String linea;
            // Leemos el archivo línea por línea
            while ((linea = brCliente.readLine()) != null) {
                // Separamos los datos de cada cliente usando el carácter ';'
                String[] partes = linea.split(";");
                // Verificamos que la línea tenga los 5 campos esperados
                if (partes.length == 5) {
                    try {
                        // Convertimos los campos de cantidad y teléfono a enteros
                        int cantidad = Integer.parseInt(partes[4]);
                        int telefono = Integer.parseInt(partes[3]);
                        // Creamos un objeto Cliente y lo añadimos a la lista
                        clientes.add(new Cliente(partes[0], partes[1], partes[2], telefono, cantidad));
                    } catch (NumberFormatException e) {
                        // Si hay un error al convertir, simplemente ignoramos esa línea
                    }
                }
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, mostramos el mensaje
            System.out.println(e.getMessage());
        }

        // Ordenamos la lista de clientes por la cantidad (descendente)
        clientes.sort((a, b) -> Integer.compare(b.getCantidad(), a.getCantidad()));

        // Mostramos los 5 clientes con mayor cantidad (o menos si hay menos de 5)
        System.out.println("Mejores 5 clientes:");
        for (int i = 0; i < Math.min(5, clientes.size()); i++) {
            Cliente c = clientes.get(i);
            // Mostramos los datos del cliente
            System.out.println(c.getDNI() + " - " + c.getNombre() + " - " + c.getEmail() + " - " + c.getTelefono() + " - " + c.getCantidad());
        }
    }

    public void actualizrCliente() {
        File archivo = new File("datos_librerias/clientes.txt");
        File temp = new File("datos_librerias/clientes-temp.txt");

        System.out.println("Dime el DNI del cliente que quieras actualizar: ");
        String DNI = sc.nextLine().trim();
        boolean encontrado = false;

        try (BufferedReader brCliente = new BufferedReader(new FileReader(archivo));
             BufferedWriter bwCliente = new BufferedWriter(new FileWriter(temp))) {

            String linea;
            while ((linea = brCliente.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5 && partes[0].trim().equalsIgnoreCase(DNI)) {
                    encontrado = true;
                    System.out.println("Dime si quieres cambiar el email o el telefono");
                    String eleccion = sc.nextLine();

                    if (eleccion.equalsIgnoreCase("telefono")) {
                        System.out.println("Dime el numero telelfono del cliente: ");
                        String nuevoTelefono = sc.nextLine();
                        linea = partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + nuevoTelefono + ";" + partes[4];
                    } else if (eleccion.equalsIgnoreCase("email")) {
                        System.out.println("Dime el nuevo email del cliente: ");
                        String nuevoEmail = sc.nextLine();
                        linea = partes[0] + ";" + partes[1] + ";" + nuevoEmail + ";" + partes[3] + ";" + partes[4];
                    }else {
                        System.out.println("Introduce una opcion valida");
                    }
                }
                bwCliente.write(linea);
                bwCliente.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (encontrado) {
            archivo.delete();
            temp.renameTo(archivo);
        } else {
            temp.delete();
            System.out.println("No tenemos clientes con ese DNI");
        }
    }
    }


