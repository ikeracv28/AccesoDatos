package NivelMedio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ExportarJSON_Biblioteca {

    // === Constantes (mismo patrón que tu clase de básico) ===
    static String fecha;
    private static final String ARCHIVO = "json/libros_";
    private static final String NODOPADRE = "biblioteca";
    private static final String NODOMEDIO = "categorias";

    private static final String INDENTACION = "    ";
    private static final String INDENTACION2 = INDENTACION + INDENTACION;
    private static final String INDENTACION3 = INDENTACION2 + INDENTACION;
    private static final String INDENTACION4 = INDENTACION3 + INDENTACION;

    private static final String CARPETA = "json";
    private static final String EXTENSION = ".json";

    // === Helpers fieles a tu estilo ===

    /** Escapa caracteres especiales para JSON. */
    private static String escapeJson(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.replace("\\", "\\\\")   // barra invertida
                .replace("\"", "\\\"")       // comillas dobles
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /** Asegura que exista el directorio de salida. */
    private static boolean crearCarpeta() {
        try {
            File dir = new File(CARPETA);
            if (!dir.exists() && !dir.mkdirs()) return false;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /** Genera nombre con timestamp yyyyMMdd_HHmmss. */
    public static String crearNombreArchivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        fecha = LocalDateTime.now().format(formatter);
        return ARCHIVO + fecha + EXTENSION;
    }

    /**
     * Exporta la lista de libros a JSON agrupado por categoría.
     *
     * Requisitos esperados del Libro:
     *  - getIsbn(), getTitulo(), getAutor(), getCategoria()
     *  - getAnioPublicacion(), getNumPaginas(), isDisponible(), getPrestamos()
     */
    public static void escribirJsonExacto(List<Libro> libros) {
        try {
            String nombreArchivo = crearNombreArchivo();

            // === VALIDACIONES (idéntico enfoque al tuyo) ===
            if (libros == null || libros.isEmpty()) {
                System.out.println("ERROR: No hay libros para exportar.");
                return;
            }
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
                return;
            }

            // === Agrupar por categoría (orden alfabético para salida estable) ===
            Map<String, List<Libro>> porCategoria = new TreeMap<>();
            for (Libro l : libros) {
                String cat = (l.getCategoria() == null || l.getCategoria().isEmpty())
                        ? "Sin categoría" : l.getCategoria();
                porCategoria.computeIfAbsent(cat, k -> new ArrayList<>()).add(l);
            }

            if (crearCarpeta()) {
                File archivo = new File(nombreArchivo);
                boolean creado = archivo.createNewFile(); // crea con timestamp
                if (!creado) {
                    System.out.println("El archivo no se ha podido crear");
                    return;
                }

                // Escribir en UTF-8, con try-with-resources
                try (BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(archivo, true), StandardCharsets.UTF_8))) {

                    // APERTURA
                    bw.write("{");
                    bw.newLine();
                    bw.write(INDENTACION + "\"" + NODOPADRE + "\": {");
                    bw.newLine();

                    // === METADATA GLOBAL ===
                    bw.write(INDENTACION2 + "\"metadata\": {");
                    bw.newLine();
                    bw.write(INDENTACION3 + "\"fecha\": \"" + escapeJson(fecha) + "\",");
                    bw.newLine();
                    bw.write(INDENTACION3 + "\"totalLibros\": " + libros.size() + ",");
                    bw.newLine();
                    bw.write(INDENTACION3 + "\"totalCategorias\": " + porCategoria.size());
                    bw.newLine();
                    bw.write(INDENTACION2 + "},");
                    bw.newLine();

                    // === ARRAY DE CATEGORÍAS ===
                    bw.write(INDENTACION2 + "\"" + NODOMEDIO + "\": [");
                    bw.newLine();

                    int iCat = 0;
                    int nCat = porCategoria.size();
                    for (Map.Entry<String, List<Libro>> entry : porCategoria.entrySet()) {
                        String categoria = entry.getKey();
                        List<Libro> lista = entry.getValue();

                        long totalPrestamos = 0;
                        for (Libro lib : lista) totalPrestamos += Math.max(0, lib.getPrestamos());

                        // Objeto categoría
                        bw.write(INDENTACION3 + "{");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"nombre\": \"" + escapeJson(categoria) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"totalLibros\": " + lista.size() + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"totalPrestamos\": " + totalPrestamos + ",");
                        bw.newLine();

                        // Array de libros de la categoría
                        bw.write(INDENTACION4 + "\"libros\": [");
                        bw.newLine();

                        for (int i = 0; i < lista.size(); i++) {
                            Libro lib = lista.get(i);

                            bw.write(INDENTACION4 + INDENTACION + "{");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"isbn\": \"" + escapeJson(nvl(lib.getIsbn())) + "\",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"titulo\": \"" + escapeJson(nvl(lib.getTitulo())) + "\",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"autor\": \"" + escapeJson(nvl(lib.getAutor())) + "\",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"anio\": " + lib.getAnioPublicacion() + ",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"paginas\": " + lib.getNumPaginas() + ",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"disponible\": " + lib.isDisponible() + ",");
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + INDENTACION + "\"prestamos\": " + lib.getPrestamos());
                            bw.newLine();
                            bw.write(INDENTACION4 + INDENTACION + "}" + (i < lista.size() - 1 ? "," : ""));
                            bw.newLine();
                        }

                        bw.write(INDENTACION4 + "]");
                        bw.newLine();
                        bw.write(INDENTACION3 + "}" + (++iCat < nCat ? "," : ""));
                        bw.newLine();
                    }

                    // CIERRES
                    bw.write(INDENTACION2 + "]");
                    bw.newLine();
                    bw.write(INDENTACION + "}");
                    bw.newLine();
                    bw.write("}");
                }

                System.out.println("JSON exportado correctamente: " + nombreArchivo);
            } else {
                System.out.println("La carpeta no se ha podido crear");
            }

        } catch (IOException e) {
            System.err.println("Error escribiendo JSON: " + e.getMessage());
        }
    }

    // === Utilidad mínima para nulls en strings ===
    private static String nvl(String s) { return (s == null) ? "" : s; }
}