import java.io.File;
public class RutasEjemplo {
    public static void main(String[] args) {
        String base = System.getProperty("user.dir");
        String sep = File.separator;

        String rutaAbsoluta = base + sep + "datos" + sep + "ejemplo.txt";
        System.out.println("Ruta absoluta generada: " + rutaAbsoluta);

        File archivo = new File(rutaAbsoluta);
        System.out.println("¿Existe el archivo? " + archivo.exists());
    }
}