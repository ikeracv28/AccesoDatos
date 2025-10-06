import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File archivo = new File("productos.txt"); // Creamos el archivo que vamos a leer

        if (!archivo.exists()) { // Si el archivo no está, avisamos y salimos
            System.out.println("El archivo no existe.");
            return;
        }

        System.out.println("Tamaño del archivo: " + archivo.length() + " bytes"); // Mostramos el tamaño del archivo

        int totalArticulos = 0; // Contador de productos
        double sumaPrecios = 0; // Suma de todos los precios
        double importeTotal = 0; // Suma de (precio * stock) de todos los productos

        // try-with-resources: el Scanner se cierra solo al acabar
        try (Scanner fileScanner = new Scanner(archivo)) {
            while (fileScanner.hasNextLine()) { // Leemos línea a línea
                String linea = fileScanner.nextLine();
                String[] campos = linea.split(";"); // Separamos los datos por ;
                if (campos.length == 4) { // Solo procesamos si hay 4 datos
                    String categoria = campos[0].trim();
                    String producto = campos[1].trim();
                    double precio = Double.parseDouble(campos[2].replace(',', '.').trim()); // Convertimos el precio a número
                    int stock = Integer.parseInt(campos[3].trim()); // Convertimos el stock a número

                    // Mostramos la info del producto
                    System.out.println(producto + " (" + categoria + ") -- Precio: " + String.format("%.2f", precio) + " €  -- Stock: " + stock);

                    totalArticulos++; // Sumamos uno al contador
                    sumaPrecios += precio; // Sumamos el precio
                    importeTotal += precio * stock; // Sumamos el importe total de ese producto
                }
            }
        } catch (FileNotFoundException e) { // Si no encuentra el archivo, lo decimos
            System.out.println("No se encontró el archivo productos.txt");
        }

        if (totalArticulos > 0) { // Si hay productos, mostramos los totales
            System.out.println("\nNúmero total de artículos: " + totalArticulos);
            System.out.println("Promedio de precios: " + String.format("%.2f", sumaPrecios / totalArticulos) + " €");
            System.out.println("Importe total: " + String.format("%.2f", importeTotal) + " €");
        }
    }
}
