package NivelMedio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExportarCSV_Biblioteca {

    // === Config igual que en tu clase de básico ===
    private static final String CARPETA = "csv";           // carpeta de salida
    private static final String SEPARADOR = ";";           // separador CSV
    private static final String PREFIJO_ARCHIVO = "csv/libros_"; // prefijo nombre
    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Crea la carpeta de salida si no existe.
     */
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

    /**
     * Escapa valores potencialmente problemáticos en CSV:
     * - Si contienen ;, comillas o saltos de línea, se encierran entre comillas
     * y se duplican las comillas internas.
     */
    private static String escaparCSV(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        if (texto.contains(SEPARADOR) || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }

    /**
     * Exporta la lista de libros agrupada por categoría, incluyendo líneas
     * separadoras con el nombre de la categoría y una línea de subtotal
     * al final de cada bloque (nº de libros y total de préstamos).
     *
     * @param libros Lista de libros a exportar (no nula ni vacía).
     */
    public void exportarCSV(List<Libro> libros) {
        if (libros == null || libros.isEmpty()) {
            System.out.println("ERROR: No hay libros para exportar.");
            return;
        }

        final String nombreArchivo = PREFIJO_ARCHIVO + LocalDateTime.now().format(TS) + ".csv";
        if (nombreArchivo.trim().isEmpty()) {
            System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
            return;
        }

        // 1) Agrupar por categoría (manteniendo orden alfabético por claridad)
        //    Puedes cambiar a LinkedHashMap si prefieres conservar el orden de entrada.
        Map<String, List<Libro>> porCategoria = new TreeMap<>();
        for (Libro l : libros) {
            String cat = (l.getCategoria() == null || l.getCategoria().isEmpty())
                    ? "Sin categoría" : l.getCategoria();
            porCategoria.computeIfAbsent(cat, k -> new ArrayList<>()).add(l);
        }
        // (El enunciado sugiere agrupar por categoría y calcular estadísticas por grupo). :contentReference[oaicite:2]{index=2}

        if (!crearCarpeta()) {
            System.out.println("ERROR: No se pudo crear/acceder a la carpeta de salida.");
            return;
        }

        // 2) Escritura del CSV
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(nombreArchivo))) {

            // Encabezado general del catálogo (línea informativa)
            bf.write("# BIBLIOTECA MUNICIPAL - CATÁLOGO DE LIBROS");
            bf.newLine();
            bf.newLine();

            // Por cada categoría, bloque con cabecera + tabla + subtotal
            for (Map.Entry<String, List<Libro>> entry : porCategoria.entrySet()) {
                String categoria = entry.getKey();
                List<Libro> lista = entry.getValue();

                // Cabecera de sección
                bf.write("# CATEGORÍA: " + categoria);
                bf.newLine();
                bf.newLine();

                // Cabecera de columnas (igual para todas las categorías)
                String header = String.join(SEPARADOR,
                        "ISBN", "Título", "Autor", "Año", "Páginas", "Disponible", "Préstamos");
                bf.write(header);
                bf.newLine();

                // Acumuladores para el subtotal de la categoría
                int totalLibros = 0;
                long totalPrestamos = 0;

                // Filas de datos
                for (Libro lib : lista) {
                    totalLibros++;
                    totalPrestamos += Math.max(0, lib.getPrestamos());

                    String linea = String.join(SEPARADOR,
                            escaparCSV(nullToEmpty(lib.getIsbn())),
                            escaparCSV(nullToEmpty(lib.getTitulo())),
                            escaparCSV(nullToEmpty(lib.getAutor())),
                            String.valueOf(lib.getAnioPublicacion()),
                            String.valueOf(lib.getNumPaginas()),
                            String.valueOf(lib.isDisponible()),
                            String.valueOf(lib.getPrestamos())
                    );
                    bf.write(linea);
                    bf.newLine();
                }

                // Línea de subtotal de la categoría (como en el ejemplo del enunciado)
                bf.newLine();
                bf.write("# Subtotal " + categoria + ": " + totalLibros + " libros, " + totalPrestamos + " préstamos");
                bf.newLine();
                bf.newLine(); // línea en blanco entre categorías
            }

            System.out.println("CSV exportado correctamente: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper para evitar "null" en CSV
    private static String nullToEmpty(Object o) {
        return (o == null) ? "" : o.toString();
    }
}