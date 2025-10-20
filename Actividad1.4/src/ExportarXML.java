import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExportarXML {

    // Método que recibe una lista de estudiantes y genera un archivo XML con sus datos
    public void excribirXML(List<Estudiante> estudiantes) {

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

        } catch (IOException e) {
            System.out.println("Error al crear el archivo XML: " + e.getMessage());
        }

        // ======================================================
        // 3️⃣ ESCRITURA DEL CONTENIDO DEL XML
        // ======================================================
        // Usamos try-with-resources para que el BufferedWriter se cierre automáticamente al finalizar
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("datos/estudiantes.xml"))) {

            // ------------------------------------------------------
            // CÁLCULO DE ESTADÍSTICAS (usando Streams de Java)
            // ------------------------------------------------------
            // Con estos cálculos evitamos escribir los valores manualmente
            // mapToDouble() transforma cada Estudiante en su nota (double)
            // average(), max(), min() calculan las estadísticas principales
            // orElse(0) devuelve 0 si la lista está vacía (evita errores)
            double notaMedia = estudiantes.stream().mapToDouble(Estudiante::getNota).average().orElse(0);
            double notaMax = estudiantes.stream().mapToDouble(Estudiante::getNota).max().orElse(0);
            double notaMin = estudiantes.stream().mapToDouble(Estudiante::getNota).min().orElse(0);

            // ------------------------------------------------------
            // ESCRITURA DE LA ESTRUCTURA XML
            // ------------------------------------------------------

            // Declaración estándar del encabezado XML
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.newLine();

            // Apertura del elemento raíz <clase>
            writer.write("<clase>");
            writer.newLine();

            // ------------------------------------------------------
            // SECCIÓN DE METADATOS
            // ------------------------------------------------------
            writer.write("  <metadata>");
            writer.newLine();

            // Fecha de creación del archivo (en este caso, fija)
            writer.write("    <fecha>19/10/2025</fecha>");
            writer.newLine();

            // Total de estudiantes incluidos en el XML
            writer.write("    <totalEstudiantes>" + estudiantes.size() + "</totalEstudiantes>");
            writer.newLine();

            writer.write("  </metadata>");
            writer.newLine();

            // ------------------------------------------------------
            // SECCIÓN DE ESTUDIANTES
            // ------------------------------------------------------
            writer.write("  <estudiantes>");
            writer.newLine();

            // Recorremos la lista recibida como parámetro y escribimos cada estudiante
            for (Estudiante e : estudiantes) {
                // Apertura del elemento <estudiante> con su atributo id
                writer.write("    <estudiante id=\"" + e.getId() + "\">");
                writer.newLine();

                // Escribimos los datos de cada campo del estudiante
                writer.write("      <nombre>" + e.getNombre() + "</nombre>");
                writer.newLine();

                // OJO: el método en tu clase debe llamarse getApellido(), no getApellido()
                writer.write("      <apellidos>" + e.getApellido() + "</apellidos>");
                writer.newLine();

                writer.write("      <edad>" + e.getEdad() + "</edad>");
                writer.newLine();

                writer.write("      <nota>" + e.getNota() + "</nota>");
                writer.newLine();

                // Cierre del elemento estudiante
                writer.write("    </estudiante>");
                writer.newLine();
            }

            // Cierre del bloque de estudiantes
            writer.write("  </estudiantes>");
            writer.newLine();

            // ------------------------------------------------------
            // SECCIÓN DE RESUMEN / ESTADÍSTICAS
            // ------------------------------------------------------
            writer.write("  <resumen>");
            writer.newLine();

            // Usamos String.format("%.2f", ...) para limitar la nota media a 2 decimales
            writer.write("    <notaMedia>" + String.format("%.2f", notaMedia) + "</notaMedia>");
            writer.newLine();

            writer.write("    <notaMaxima>" + notaMax + "</notaMaxima>");
            writer.newLine();

            writer.write("    <notaMinima>" + notaMin + "</notaMinima>");
            writer.newLine();

            writer.write("  </resumen>");
            writer.newLine();

            // ------------------------------------------------------
            // CIERRE DEL DOCUMENTO
            // ------------------------------------------------------
            writer.write("</clase>");
            writer.newLine();

            // Mensaje por consola para confirmar que todo ha ido bien
            System.out.println("Archivo XML generado correctamente.");

        } catch (IOException e) {
            // Si ocurre un error durante la escritura, lo mostramos por consola
            System.out.println("Error al escribir XML: " + e.getMessage());
        }
    }
}

/* forma de la que lo he hecho yo manulmente, una vez hecho, le he pregvuntado a cht como hago par no
tener que ir escribiendo yo los alumnos uno a uno, si no darle una lista y que me los añada
        try{
            // Estructura básica para escribir CSV
            BufferedWriter writer = new BufferedWriter(new FileWriter("datos/estudiantes.xml", true));

            // Declaración XML
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.newLine();

            // Elemento raíz
            writer.write("<clase>");
            writer.newLine();


            // metadata
            writer.write("<metadata>");
            writer.newLine();

            // elemntos metadata
            writer.write("  <fecha>19/10/2025</fecha>");
            writer.newLine();
            writer.write("  <totalEstudiantes>5</totalEstudiantes>");
            writer.newLine();

            writer.write("</metadata>");
            writer.newLine();


            // metadata
            writer.write("<estudiantes>");
            writer.newLine();

            // elemntos hijos
            writer.write("  <estudiante id=\"1\">");
            writer.newLine();
            writer.write("  <nombre>Juan</nombre>");
            writer.newLine();
            writer.write("  <apellidos>García López</apellidos>");
            writer.newLine();
            writer.write("  <edad>20</edad>");
            writer.newLine();
            writer.write("  <nota>8.5</nota>");
            writer.newLine();
            writer.write("  </estudiante>");
            writer.newLine();

            writer.write("</estudiantes>");
            writer.newLine();


            // metadata
            writer.write("<estudiantes>");
            writer.newLine();

            // elemntos hijos
            writer.write("  <resumen>");
            writer.newLine();
            writer.write("  <notaMedia>8.18</notaMedia>");
            writer.newLine();
            writer.write("  <notaMaxima>9.2</notaMaxima>");
            writer.newLine();
            writer.write("  <notaMinima>6.5</notaMinima>");
            writer.newLine();
            writer.write("  </resumen>");
            writer.newLine();

            writer.write("</estudiantes>");
            writer.newLine();

            // Cierre elemento raíz
            writer.write("</clase>");
            writer.newLine();

            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
*/

