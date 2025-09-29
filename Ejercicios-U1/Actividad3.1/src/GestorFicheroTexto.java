import java.io.*;
public class GestorFicheroTexto {
    public static void main(String[] args) {
        try {
// Escritura
            FileWriter fw = new FileWriter("datos/registro.txt");
            // BufferedWriter bw = new BufferedWriter(fw);

            fw.write("Registro 1");
            // fw.newLine();
            fw.write("Registro 2");
            //fw.newLine();
            fw.write("Registro 3");
            //fw.newLine();
            fw.flush();
            fw.close();
            System.out.println("Archivo escrito con éxito.");
// Lectura
            FileReader fr = new FileReader("datos/registro.txt");
            BufferedReader br = new BufferedReader(fr);

            String linea;
            System.out.println("Contenido del archivo:");
            while ((linea = br.readLine()) != null) {
                System.out.println("> " + linea);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}