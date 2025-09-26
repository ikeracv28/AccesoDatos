import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LecturaSecuencial {
    public static void main(String[] args) {
        try{
            File archivo = new File("datos.txt");
            BufferedReader br= new BufferedReader(new FileReader(archivo));
            String linea;
            System.out.println("Lectura del archivo (modo secuencial): ");
            while ((linea = br.readLine()) != null) {
                System.out.println("> " +linea);
            }
            br.close();
        } catch (IOException e){
            System.out.println("Error al leer el archivo: " + e.getMessage() );
        }
    }
}

