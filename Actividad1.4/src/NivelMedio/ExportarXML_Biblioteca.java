package NivelMedio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ExportarXML_Biblioteca {

    // === Constantes en el mismo estilo que tu clase ===
    static String fecha;
    private static final String ARCHIVO = "xml/libros_";     // mismo prefijo que usas, pero para libros
    private static final String NODOPADRE = "biblioteca";
    private static final String NODOMEDIO = "categorias";
    private static final String NODOCATEG = "categoria";
    private static final String NODOLIBRO = "libro";

    private static final String INDENTACION = "    ";
    private static final String INDENTACION2 = INDENTACION + INDENTACION;
    private static final String INDENTACION3 = INDENTACION2 + INDENTACION;
    private static final String INDENTACION4 = INDENTACION3 + INDENTACION;

    private static final String CARPETA = "XML";  // fiel a tu mayúscula
    private static final String EXTENSION = ".xml";

    // === Utilidades exactamente en tu estilo ===

    /**
     * Escapa caracteres XML especiales.
     */
    private static String escapeXml(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /**
     * Crea la carpeta de salida si no existe.
     */
    private static boolean crearCarpeta() {
        try {
            File dir = new File(CARPETA);
            if (!dir.exists() && !dir.mkdirs()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Genera nombre con timestamp yyyyMMdd_HHmmss,
     * exactamente como en tu clase base.
     */
    public static String crearNombreArchivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        fecha = LocalDateTime.now().format(formatter);
        return ARCHIVO + fecha + EXTENSION;
    }

    /**
     * Exporta la lista de libros a un XML agrupado por categoría,
     * incluyendo totales por categoría y metadatos globales.
     *
     * Requisitos esperados del Libro:
     *  - getIsbn(), getTitulo(), getAutor(), getCategoria()
     *  - getAnioPublicacion(), getNumPaginas(), isDisponible(), getPrestamos()
     *
     * @param libros lista de libros (no nula ni vacía)
     */
    public static void escribirXmlExacto(List<Libro> libros) {
        try {
            String nombreArchivo = crearNombreArchivo();

            if (libros == null || libros.isEmpty()) {
                System.out.println("ERROR: No hay libros para exportar.");
                return;
            }

            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
                return;
            }

            // === Agrupación por categoría (orden alfabético para salida estable) ===
            Map<String, List<Libro>> porCategoria = new TreeMap<>();
            for (Libro l : libros) {
                String cat = (l.getCategoria() == null || l.getCategoria().isEmpty())
                        ? "Sin categoría" : l.getCategoria();
                porCategoria.computeIfAbsent(cat, k -> new ArrayList<>()).add(l);
            }

            if (crearCarpeta()) {

                // Totales globales
                int totalLibros = libros.size();
                long totalCategorias = porCategoria.size();

                File archivo = new File(nombreArchivo);
                boolean creado = archivo.createNewFile();
                if (!creado) {
                    System.out.println("El archivo no se ha podido crear");
                    return;
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

                // === Cabecera XML + raíz ===
                bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                bw.newLine();
                bw.write("<" + NODOPADRE + ">");
                bw.newLine();

                // === Metadata global ===
                bw.write(INDENTACION + "<metadata>");
                bw.newLine();
                bw.write(INDENTACION2 + "<fecha>" + escapeXml(fecha) + "</fecha>");
                bw.newLine();
                bw.write(INDENTACION2 + "<totalLibros>" + totalLibros + "</totalLibros>");
                bw.newLine();
                bw.write(INDENTACION2 + "<totalCategorias>" + totalCategorias + "</totalCategorias>");
                bw.newLine();
                bw.write(INDENTACION + "</metadata>");
                bw.newLine();
                bw.newLine();

                // === Nodo contenedor de categorías ===
                bw.write(INDENTACION + "<" + NODOMEDIO + ">");
                bw.newLine();

                // === Bloque por categoría ===
                for (Map.Entry<String, List<Libro>> entry : porCategoria.entrySet()) {
                    String categoria = entry.getKey();
                    List<Libro> lista = entry.getValue();

                    // totales por categoría
                    long totalPrestamosCat = 0;
                    for (Libro lib : lista) {
                        totalPrestamosCat += Math.max(0, lib.getPrestamos());
                    }

                    // <categoria nombre="..." totalLibros="N" totalPrestamos="M">
                    bw.write(INDENTACION2 + "<" + NODOCATEG
                            + " nombre=\"" + escapeXml(categoria) + "\""
                            + " totalLibros=\"" + lista.size() + "\""
                            + " totalPrestamos=\"" + totalPrestamosCat + "\">");
                    bw.newLine();

                    // Cada libro como elemento con atributos y elementos hijo
                    for (Libro lib : lista) {
                        String isbn = lib.getIsbn() == null ? "" : lib.getIsbn();
                        bw.write(INDENTACION3 + "<" + NODOLIBRO
                                + " isbn=\"" + escapeXml(isbn) + "\""
                                + " disponible=\"" + lib.isDisponible() + "\">");
                        bw.newLine();

                        bw.write(INDENTACION4 + "<titulo>" + escapeXml(lib.getTitulo()) + "</titulo>");
                        bw.newLine();
                        bw.write(INDENTACION4 + "<autor>" + escapeXml(lib.getAutor()) + "</autor>");
                        bw.newLine();
                        bw.write(INDENTACION4 + "<anio>" + lib.getAnioPublicacion() + "</anio>");
                        bw.newLine();
                        bw.write(INDENTACION4 + "<paginas>" + lib.getNumPaginas() + "</paginas>");
                        bw.newLine();
                        bw.write(INDENTACION4 + "<prestamos>" + lib.getPrestamos() + "</prestamos>");
                        bw.newLine();

                        bw.write(INDENTACION3 + "</" + NODOLIBRO + ">");
                        bw.newLine();
                    }

                    bw.write(INDENTACION2 + "</" + NODOCATEG + ">");
                    bw.newLine();
                    bw.newLine();
                }

                // Cierre del contenedor de categorías y raíz
                bw.write(INDENTACION + "</" + NODOMEDIO + ">");
                bw.newLine();
                bw.write("</" + NODOPADRE + ">");
                bw.newLine();

                bw.close();
                System.out.println("XML exportado correctamente: " + nombreArchivo);
            }

        } catch (IOException e) {
            System.err.println("Error escribiendo XML: " + e.getMessage());
        }
    }
}