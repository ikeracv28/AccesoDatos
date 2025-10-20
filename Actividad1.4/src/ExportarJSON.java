import java.io.File;
import java.io.IOException;

public class ExportarJSON {
    // ======================================================
    // 1️⃣ CREACIÓN DE LA CARPETA "datos" (si no existe)
    // ======================================================
        try {
        File carpeta = new File("datos"); // Creamos un objeto File apuntando a la carpeta "datos"

        // Si la carpeta no existe, la creamos con mkdir()
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

    } catch (Exception e) {
        // Si ocurre algún error (por ejemplo, permisos denegados), mostramos el mensaje
        System.out.println("Error al crear la carpeta: " + e.getMessage());
    }

    // ======================================================
    // 2️⃣ CREACIÓN DEL ARCHIVO "estudiantes.xml"
    // ======================================================
        try {
        File archivo = new File("datos/estudiantes.xml"); // Creamos el archivo dentro de la carpeta "datos"

        // Si el archivo no existe aún, se crea con createNewFile()
        if (!archivo.exists()) {
            archivo.createNewFile();
        }

    } catch (
    IOException e) {
        System.out.println("Error al crear el archivo XML: " + e.getMessage());
    }
        try{

    }
}

