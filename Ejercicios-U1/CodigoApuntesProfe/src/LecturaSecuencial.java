import java.io.*;

public class LecturaSecuencial {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("datos.txt"));
        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

        br.close();
    }
}