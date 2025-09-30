import java.io.*;

public class AccesoAleatorio {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("datos.txt", "rw");

        raf.seek(20); // Mover el puntero a la posición 20 (byte 20)
        String linea = raf.readLine(); // Leer desde ahí
        System.out.println("Contenido desde byte 20: " + linea);

        raf.close();
    }
}