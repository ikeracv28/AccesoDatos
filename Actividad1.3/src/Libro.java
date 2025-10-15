import java.io.*;
import java.util.Scanner;

public class Libro {
    private String ISBN;
    private String titulo;
    private String autor;
    private String categoria;
    private double precio;
    private int stock;
    static Scanner sc = new Scanner(System.in);

    public Libro() {
    }

    public Libro(String ISBN, String titulo, String autor, String categoria, double precio, int stock) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void crearArchivosLibro() {
        File fDtos = new File("datos_librerias");
        if (!fDtos.exists()) {
            fDtos.mkdir();
        }
        try {
            File fLibros = new File("datos_librerias/libros.txt");
            if (!fLibros.exists()) {
                fLibros.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo que vamos a llamar para guardar libros
    public void guardarLibro(Libro libro) {
        String ISBN = libro.getISBN();
        String titulo = libro.getTitulo();
        String autor = libro.getAutor();
        String categoria = libro.getCategoria();
        double precio = libro.getPrecio();
        int stock = libro.getStock();

        try (BufferedWriter bwLibro = new BufferedWriter(new FileWriter("datos_librerias/libros.txt", true))) {
            if (validarISBN(ISBN)) {
                String linea = (ISBN + ";" + titulo + ";" + autor + ";" + categoria + ";" + precio + ";" + stock);
                bwLibro.write(linea);
            } else {
                System.out.println("El ISBN ya lo tenemos registrado");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean validarISBN(String ISBN) {
        try (BufferedReader brLibro = new BufferedReader(new FileReader("datos_librerias/libros.txt"))) {
            String linea;
            while ((linea = brLibro.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    if (partes[0].equalsIgnoreCase(ISBN)) {
                        return false;

                    } else {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public void nuevoLibro() {
        try {
            System.out.println("Ingresa el ISBN del nuevo libro: ");
            String ISBN = sc.nextLine();
            if (validarISBN(ISBN)) {
                System.out.println("Dime el titulo del libro: ");
                String titulo = sc.nextLine();
                System.out.println("Dime el autor del libro: ");
                String autor = sc.nextLine();
                System.out.println("Dime el categoria del libro: ");
                String categoria = sc.nextLine();
                System.out.println("Dime el precio del libro: ");
                double precio = sc.nextDouble();
                sc.nextLine();
                System.out.println("Dime el stock del libro: ");
                int stock = sc.nextInt();
                sc.nextLine();

                guardarLibro(new Libro(ISBN, titulo, autor, categoria, precio, stock));

            } else {
                System.out.println("El ISBN ya lo tenemos registrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void mostrarLibros(){
        try(BufferedReader brLibro = new BufferedReader(new FileReader("datos_librerias/libros.txt"))) {
            String linea;
            while ((linea = brLibro.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    System.out.println(partes[0] + " - " + partes[1] +  " - " + partes[2] + " - " + partes[3] + " - " + partes[4] +  " - " + partes[5]);
                }else{
                    System.out.println("No hay mas libros");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarLibroISBN() {
        System.out.println("Dime el ISBN del libro para comprobar si lo tenemos: ");
        String ISBN = sc.nextLine();
        if (validarISBN(ISBN)) {
            System.out.println("Este libro no lo tenemos registrado");
        }else  {
            System.out.println("El ISBN ya lo tenemos registrado");
        }
    }

    public void buscarPorCategoria(){
        try(BufferedReader brLibro = new BufferedReader(new FileReader("datos_librerias/libros.txt"))) {
            System.out.println("Dime el categoria del libro: ");
            String categoria = sc.nextLine();
            String linea;
            while ((linea = brLibro.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    if (partes[3].equalsIgnoreCase(categoria)) {
                        System.out.println(partes[0] + " - " + partes[1] + " - " + partes[2] + " - " + partes[3] + " - " + partes[4] + " - " + partes[5]);
                    }else{
                        System.out.println("El categoria del libro no lo tenemos registrado");
                }
            }
        }
            System.out.println("Ya no hay mas libros en esa categoria");

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void actualizarStock() {
        // archivo original donde están guardados los libros
        File archivo = new File("datos_librerias/libros.txt");
        // archivo temporal que usaremos para reescribir con el stock actualizado
        File temp = new File("datos_librerias/libros_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

            // pedimos el isbn del libro al usuario
            System.out.println("Dime el ISBN del libro que quieres actualizar el stock: ");
            String ISBN = sc.nextLine();

            // usamos tu método validarISBN()
            // si devuelve true → el ISBN no existe
            // si devuelve false → el libro sí existe
            if (validarISBN(ISBN)) {
                System.out.println(" Este libro no está registrado en la librería.");
                return; // salimos del método
            }

            // le pedimos al usuario cuántas unidades nuevas llegaron
            System.out.println("Dime cuántas unidades nuevas recibiste: ");
            int unidadesNuevas = sc.nextInt();
            sc.nextLine(); // limpiamos el buffer del scanner

            String linea;
            // leemos cada línea del archivo original
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    // si el isbn coincide con el introducido por el usuario
                    if (partes[0].equalsIgnoreCase(ISBN)) {
                        // convertimos el stock actual de String a entero
                        int stockActual = Integer.parseInt(partes[5]);
                        // sumamos las unidades nuevas
                        int nuevoStock = stockActual + unidadesNuevas;
                        // actualizamos el valor en el array
                        partes[5] = String.valueOf(nuevoStock);

                        System.out.println(" Stock actualizado correctamente. Nuevo stock: " + nuevoStock);
                    }
                    // reconstruimos la línea (actualizada o no)
                    linea = String.join(";", partes);
                }
                // escribimos la línea en el archivo temporal
                bw.write(linea);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al actualizar el stock: " + e.getMessage());
        }

        // reemplazamos el archivo original con el nuevo (ya modificado)
        if (temp.exists()) {
            archivo.delete();   // borramos el archivo original
            temp.renameTo(archivo); // renombramos el temporal con el nombre original
        }
    }

    public void avisoStockMenor5(){
        try(BufferedReader brLibro = new BufferedReader(new FileReader("datos_librerias/libros.txt"))){

            String linea;
            while((linea = brLibro.readLine()) != null){
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    int stock =  Integer.parseInt(partes[5]);
                        if (stock < 5) {
                            System.out.println("Hay que reponer stock de este libro: " + partes[0] + " - " + partes[1] + " - " + partes[2] + " - " + partes[3] + " - " + partes[4] + " - " + partes[5]);
                        }
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}



