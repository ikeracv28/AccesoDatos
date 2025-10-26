package NivelBasico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarCSV {

    // Método que recibe la lista de estudiantes y genera un archivo CSV con sus datos
    public void escribirCSV(List<Estudiante> estudiantes) {

        // ======================================================
        // 1️⃣ CREACIÓN DE LA CARPETA "datos" (si no existe)
        // ======================================================
        try {
            File carpeta = new File("datos"); // Creamos un objeto File apuntando a la carpeta "datos"

            // Si la carpeta aún no existe, se crea con mkdir()
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

        } catch (Exception e) {
            // Captura de cualquier error (por ejemplo, permisos denegados)
            System.out.println("Error al crear la carpeta: " + e.getMessage());
        }

        // ======================================================
        // 2️⃣ CREACIÓN DEL ARCHIVO "estudiantes.csv"
        // ======================================================
        try {
            File archivo = new File("datos/estudiantes.csv"); // Creamos el archivo dentro de la carpeta "datos"

            // Si el archivo no existe todavía, lo creamos
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Error al crear el archivo CSV: " + e.getMessage());
        }

        // ======================================================
        // 3️⃣ ESCRITURA DEL CONTENIDO DEL ARCHIVO CSV
        // ======================================================
        // Usamos try-with-resources para que el BufferedWriter se cierre automáticamente al final
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("datos/estudiantes.csv"))) {

            // ------------------------------------------------------
            // ENCABEZADO DEL CSV
            // ------------------------------------------------------
            // Cada campo se separa con punto y coma (;)
            writer.write("ID;Nombre;Apellidos;Edad;Nota");
            writer.newLine(); // Salto de línea para pasar a los datos

            // ------------------------------------------------------
            // ESCRIBIR LOS DATOS DE CADA ESTUDIANTE
            // ------------------------------------------------------
            // Recorremos la lista que nos llegó desde el Main
            for (Estudiante e : estudiantes) {

                // Escribimos cada atributo separado por punto y coma (;)
                // Ejemplo: 1;Juan;García López;20;8.5
                writer.write(
                        e.getId() + ";" +
                                e.getNombre() + ";" +
                                e.getApellido() + ";" +
                                e.getEdad() + ";" +
                                e.getNota()
                );
                writer.newLine(); // Salto de línea después de cada estudiante
            }

            // ------------------------------------------------------
            // CÁLCULO DE LA NOTA MEDIA (usando Streams)
            // ------------------------------------------------------
            // Calculamos la media de todas las notas automáticamente
            // Si la lista está vacía, orElse(0) evita un error devolviendo 0
            double notaMedia = estudiantes.stream()
                    .mapToDouble(Estudiante::getNota)
                    .average()
                    .orElse(0);

            // ------------------------------------------------------
            // LÍNEA FINAL CON RESUMEN
            // ------------------------------------------------------
            // Añadimos una línea especial que indique la nota media general del grupo
            // Se usa String.format("%.2f") para mostrar solo dos decimales
            writer.write("# Nota media;" + String.format("%.2f", notaMedia));
            writer.newLine();

            // Mensaje por consola para confirmar que todo se generó correctamente
            System.out.println("Archivo CSV generado correctamente.");

        } catch (IOException e) {
            // Captura de errores durante la escritura
            System.out.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}
        /*manera de hcerlo con los puntes de clase a manoo, sirve si tenemos pocos clientes, a la que sean muchos muy pesado
        try{
            // Estructura básica para escribir CSV
            BufferedWriter writer = new BufferedWriter(new FileWriter("datos/estudiantes.csv", true));

            // Escribir encabezado
            writer.write("ID;Nombre;Apellidos;Edad;Nota");
            writer.newLine();

            // Escribir datos
            writer.write("1;Juan;García López;20;8.5");
            writer.newLine();
            writer.write("2;María;Rodríguez;19;9.2");
            writer.newLine();
            writer.write("3;Pedro;Martínez;21;7.8");
            writer.newLine();
            writer.write("4;Ana;López;20;8.9");
            writer.newLine();
            writer.write("5;Carlos;Sánchez;22;6.5");
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.write("#Nota media;8.18" );



            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
*/