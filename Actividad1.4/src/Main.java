import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {


        // Crear lista de estudiantes (solo una vez)
        List<Estudiante> estudiantes = Arrays.asList(
                new Estudiante(1, "Juan", "García López", 20, 8.5),
                new Estudiante(2, "María", "Rodríguez", 19, 9.2),
                new Estudiante(3, "Pedro", "Martínez", 21, 7.8),
                new Estudiante(4, "Ana", "López", 20, 8.9),
                new Estudiante(5, "Carlos", "Sánchez", 22, 6.5)
        );

        //ExportarCSV exportarCSV = new ExportarCSV();
        //exportarCSV.escribirCSV(estudiantes);

        ExportarCSVProfe exportarCSVProfe = new  ExportarCSVProfe();
        exportarCSVProfe.exportarCSV(estudiantes);

        ExportarXML exportarXML = new ExportarXML();

        exportarXML.excribirXML(estudiantes);
        ExportarJSON exportarJSON = new ExportarJSON();
        exportarJSON.escribirJsonExacto(estudiantes);
    }
}
