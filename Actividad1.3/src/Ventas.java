import java.io.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ventas {
    String DNI;
    String ISBN;
    int unidades;
    String fecha;
    double totalVentas;
    Cliente cliente ;
    Libro libro;
    static Scanner sc = new Scanner(System.in);

    public Ventas(){}

    public Ventas(String DNI, String ISBN, int unidades, double totalVentas) {
        this.DNI = DNI;
        this.ISBN = ISBN;
        this.unidades = unidades;
        this.totalVentas = totalVentas;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public void crearArchivos(){
        try{
            File file = new File("datos_librerias");
            if (!file.exists()){
                file.mkdir() ;
            }
            File farchivo = new File("datos_librerias/ventas.txt");
            if (!farchivo.exists()){
                farchivo.createNewFile();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void guardarVenta(Ventas venta){
        try(BufferedWriter bwGuardarVenta = new BufferedWriter(new FileWriter("datos_librerias/ventas.txt",true))){
            Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            String linea = venta.getDNI() + ";" + venta.getISBN() + ";" + venta.getUnidades() + ";" + formato.format(fecha) + ";" + venta.getTotalVentas();


            if(actualizarCantidad(venta.getDNI(), venta.getUnidades()) && actualizarStock(venta.getISBN(), venta.getUnidades())) {
                    bwGuardarVenta.write(linea);
                     bwGuardarVenta.newLine();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean actualizarCantidad(String DNI, int unidades){
        File archivo = new File("datos_librerias/clientes.txt");
        File temp = new File("datos_librerias/clientes-temp.txt");
        boolean actualizado = false;
        try (BufferedReader brCliente = new BufferedReader(new FileReader(archivo));
             BufferedWriter bwCliente = new BufferedWriter(new FileWriter(temp, true) )) {
            String linea;
            while((linea = brCliente.readLine()) != null){
                String[] partes = linea.split(";");
                if(partes.length == 5){
                    if(DNI.equalsIgnoreCase(partes[0])){
                        int cantidadVieja = Integer.parseInt(partes[4]);
                        int cantidadNueva = unidades + cantidadVieja ;
                        linea = partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" + cantidadNueva;
                        actualizado = true;
                    }
                }
                bwCliente.write(linea);
                bwCliente.newLine();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
    }
        if(temp.exists()){
            archivo.delete();
            temp.renameTo(archivo);
        }
        if (actualizado == false){
            System.out.println("No tenemos ese dni registardo");
        }
        return actualizado;
    }

    public boolean actualizarStock(String ISBN, int unidades) {
        // archivo original donde están guardados los libros
        File archivo = new File("datos_librerias/libros.txt");
        // archivo temporal que usaremos para reescribir con el stock actualizado
        File temp = new File("datos_librerias/libros_temp.txt");
        boolean actualizado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    if (ISBN.equalsIgnoreCase(partes[0])) {
                        int stockAntiguo = Integer.parseInt(partes[5]);
                        if (stockAntiguo >= unidades) {
                            int stockNuevo = stockAntiguo - unidades;
                            linea = partes[0] + ";" + partes[1] + ";" + partes[2] + ";" + partes[3] + ";" + partes[4] + ";" + stockNuevo;
                            actualizado = true;
                        }
                    }
                }
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (temp.exists()) {
            archivo.delete();
            temp.renameTo(archivo);
        }
        if(actualizado == false){
            System.out.println("No hay suficiente cantidad o no tenenemos ese ISBN registardo");
        }
        return actualizado;
    }

    public double calcularTotal(String ISBN, int unidades){
       double precioTotal = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("datos_librerias/libros.txt"))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] partes = linea.split(";");
                if (partes[0].equalsIgnoreCase(ISBN)){
                    double precioLibro = Double.parseDouble(partes[4]);
                    precioTotal = unidades * precioLibro;

                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return precioTotal;
    }


    public void nuevaVenta(){
        try{
            System.out.println("Dime el DNI del cliente que quiere comprar: ");
            String DNI = sc.nextLine();
            System.out.println("Diime el ISBN del libro que quieres comprar: ");
            String ISBN = sc.nextLine();
            System.out.println("Dime la cantidad de libros que quieres comprar: ");
            int cantidad = sc.nextInt();
            sc.nextLine();
            double precioTotal = calcularTotal(ISBN, cantidad);
            guardarVenta(new Ventas(DNI, ISBN, cantidad, precioTotal));

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void buscarPorFecha(){
        try(BufferedReader brVentas = new BufferedReader(new FileReader("datos_librerias/ventas.txt"))){
            System.out.println("Dime la fecha que quieres buscar");
            String fecha = sc.nextLine();
            String linea;
            while((linea = brVentas.readLine()) != null){
                String partes[] = linea.split(";");
                if (partes[4].equalsIgnoreCase(fecha)){
                    System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4]);
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void buscarPorClientes(){
        try(BufferedReader brVentas = new BufferedReader(new FileReader("datos_librerias/ventas.txt"))){
            System.out.println("Dime el DNI del cliente que quieres buscar sus ventas: ");
            String DNI = sc.nextLine();
            String linea;
            while((linea = brVentas.readLine())!= null){
                String[] partes = linea.split(";");
                if (partes[0].equalsIgnoreCase(DNI)){
                    System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4]);
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void buscarPorISBN(){
        try(BufferedReader brVentas = new BufferedReader(new FileReader("datos_librerias/ventas.txt"))){
            System.out.println("Dime el ISBN del Libro que quieres buscar sus ventas: ");
            String ISBN = sc.nextLine();
            String linea;
            while((linea = brVentas.readLine())!= null){
                String[] partes = linea.split(";");
                if (partes[1].equalsIgnoreCase(ISBN)){
                    System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4]);
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void calcularTotalTodasVentas(){
        try(BufferedReader brVentas = new BufferedReader(new FileReader("datos_librerias/ventas.txt"))){
            double totalTodasVentas = 0;
            String linea;
            while ((linea = brVentas.readLine()) != null){
                String[] partes = linea.split(";");
                totalTodasVentas += Double.parseDouble(partes[4]);
            }
            System.out.println("El total de todas las ventas es de: " + totalTodasVentas);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    }

