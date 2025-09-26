import java.io.IOException;
import java.io.RandomAccessFile;

public class AccesoAleatorio {
    public static void main(String[] args) {
        try{
            RandomAccessFile raf = new RandomAccessFile("datos.txt", "r");
            long posicion = 15;
            raf.seek(posicion);

            System.out.println("Lectura desde byte " + posicion + ":");
            String linea= raf.readLine();
            System.out.println("> " + linea);

            raf.close();
        } catch (IOException e){
            System.out.println("Error en acceso aleatorio: " + e.getMessage() );
        }

    }
}
