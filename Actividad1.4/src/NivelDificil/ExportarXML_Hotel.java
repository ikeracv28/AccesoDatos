package NivelDificil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * NIVEL 3 — 3.2 XML: Exporta RESERVAS del hotel manteniendo relaciones Cliente/Habitación,
 * e incluye estadísticas por tipo de habitación y por estado.
 *
 * Estructura (resumen):
 * <?xml version="1.0" encoding="UTF-8"?>
 * <hotel>
 *   <informacion>
 *     <nombre>Hotel Paradise</nombre>
 *     <fecha>dd/MM/yyyy</fecha>
 *   </informacion>
 *   <reservas totalReservas="N">
 *     <reserva id="1" estado="Confirmada">
 *       <cliente>...</cliente>
 *       <habitacion numero="101" tipo="Doble">...</habitacion>
 *       <fechas>...</fechas>
 *       <precio>...</precio>
 *     </reserva>
 *     ...
 *   </reservas>
 *   <estadisticas>
 *     <porTipoHabitacion>...</porTipoHabitacion>
 *     <porEstado>...</porEstado>
 *     <resumen>...</resumen>
 *   </estadisticas>
 * </hotel>
 */
public class ExportarXML_Hotel {

    // === Constantes (fiel a tu clase XML previa) ===
    static String fecha; // timestamp para nombre de archivo (yyyyMMdd_HHmmss)
    private static final String ARCHIVO = "xml/reservas_";
    private static final String NODOPADRE = "hotel";

    // Nodos/secciones
    private static final String NODO_INFO = "informacion";
    private static final String NODO_RESERVAS = "reservas";
    private static final String NODO_RESERVA = "reserva";
    private static final String NODO_CLIENTE = "cliente";
    private static final String NODO_HAB = "habitacion";
    private static final String NODO_FECHAS = "fechas";
    private static final String NODO_PRECIO = "precio";
    private static final String NODO_ESTAD = "estadisticas";

    // Indentación (exacto a tu estilo)
    private static final String INDENTACION = "    ";
    private static final String INDENTACION2 = INDENTACION + INDENTACION;
    private static final String INDENTACION3 = INDENTACION2 + INDENTACION;
    private static final String INDENTACION4 = INDENTACION3 + INDENTACION;

    private static final String CARPETA = "XML"; // igual que tu clase
    private static final String EXTENSION = ".xml";

    // Formatos
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Información del hotel (puedes cambiarlo si lo necesitas)
    private static final String NOMBRE_HOTEL = "Hotel Paradise";

    // ======= Helpers fieles a tu patrón =======

    /**
     * Escapa caracteres especiales para XML.
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
     * Genera el nombre del archivo con timestamp.
     */
    public static String crearNombreArchivo() {
        fecha = LocalDateTime.now().format(TS);
        return ARCHIVO + fecha + EXTENSION;
    }

    // ======= Cálculos auxiliares =======

    /** dd/MM/yyyy o "" si null. */
    private static String f(LocalDate d) {
        return (d == null) ? "" : DF.format(d);
    }

    /** Noches entre entrada (inclusive) y salida (exclusive), 0 si fechas nulas o inválidas. */
    private static int calcNoches(LocalDate entrada, LocalDate salida, int fallback) {
        if (entrada != null && salida != null) {
            long n = ChronoUnit.DAYS.between(entrada, salida);
            return (int) Math.max(0, n);
        }
        return Math.max(0, fallback);
    }

    /** Formatea double a 2 decimales con punto. */
    private static String d2(double v) {
        return String.format(java.util.Locale.US, "%.2f", v);
    }

    /**
     * Exporta a XML manteniendo anidación (cliente/habitación/fechas/precio)
     * y añadiendo estadísticas por tipo y estado.
     */
    public static void escribirXmlExacto(List<Reserva> reservas) {
        try {
            String nombreArchivo = crearNombreArchivo();

            // === VALIDACIONES como en tu clase ===
            if (reservas == null || reservas.isEmpty()) {
                System.out.println("ERROR: No hay productos para exportar.");
                return;
            }
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
                return;
            }

            // === Preparación de agregados ===
            // Orden por fecha de entrada e id para salida estable
            reservas.sort(Comparator
                    .comparing(Reserva::getFechaEntrada, Comparator.nullsLast(LocalDate::compareTo))
                    .thenComparing(Reserva::getId));

            // Estadísticas
            Map<String, Integer> countPorEstado = new LinkedHashMap<>();
            Map<String, Integer> countPorTipo = new LinkedHashMap<>();
            Map<String, Double> ingresosPorTipo = new LinkedHashMap<>();

            int totalReservas = 0;
            long nochesReservadas = 0;
            double ingresosTotal = 0.0;

            for (Reserva r : reservas) {
                totalReservas++;

                Habitacion h = r.getHabitacion();
                String tipo = (h == null || h.getTipo() == null || h.getTipo().isEmpty())
                        ? "Desconocido" : h.getTipo();

                LocalDate e = r.getFechaEntrada();
                LocalDate s = r.getFechaSalida();
                int noches = calcNoches(e, s, r.getNoches());
                double precioNoche = (h == null) ? 0.0 : h.getPrecioPorNoche();
                double total = noches * precioNoche;

                nochesReservadas += noches;
                ingresosTotal += total;

                countPorTipo.merge(tipo, 1, Integer::sum);
                ingresosPorTipo.merge(tipo, total, Double::sum);

                String estado = (r.getEstado() == null || r.getEstado().isEmpty())
                        ? "Desconocido" : r.getEstado();
                countPorEstado.merge(estado, 1, Integer::sum);
            }

            // Ocupación media (aproximación): no definida en XML, pero por si la quisieras calcular.
            // Se deja fuera del XML para ser fiel al enunciado.

            if (crearCarpeta()) {
                File archivo = new File(nombreArchivo);
                boolean creado = archivo.createNewFile();
                if (!creado) {
                    System.out.println("El archivo no se ha podido crear");
                    return;
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

                // === Declaración y raíz ===
                bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                bw.newLine();
                bw.write("<" + NODOPADRE + ">");
                bw.newLine();

                // --- <informacion> ---
                bw.write(INDENTACION + "<" + NODO_INFO + ">");
                bw.newLine();
                bw.write(INDENTACION2 + "<nombre>" + escapeXml(NOMBRE_HOTEL) + "</nombre>");
                bw.newLine();
                bw.write(INDENTACION2 + "<fecha>" + escapeXml(LocalDate.now().format(DF)) + "</fecha>");
                bw.newLine();
                bw.write(INDENTACION + "</" + NODO_INFO + ">");
                bw.newLine();

                // --- <reservas totalReservas="..."> ---
                bw.write(INDENTACION + "<" + NODO_RESERVAS + " totalReservas=\"" + totalReservas + "\">");
                bw.newLine();

                // Reservas
                for (Reserva r : reservas) {
                    Habitacion h = r.getHabitacion();
                    Cliente c = r.getCliente();

                    LocalDate e = r.getFechaEntrada();
                    LocalDate s = r.getFechaSalida();

                    int noches = calcNoches(e, s, r.getNoches());
                    double precioNoche = (h == null) ? 0.0 : h.getPrecioPorNoche();
                    double total = noches * precioNoche;

                    // <reserva id=".." estado="..">
                    bw.write(INDENTACION2 + "<" + NODO_RESERVA
                            + " id=\"" + r.getId() + "\""
                            + " estado=\"" + escapeXml(r.getEstado()) + "\">");
                    bw.newLine();

                    // <cliente>
                    bw.write(INDENTACION3 + "<" + NODO_CLIENTE + ">");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<id>" + ((c == null) ? "" : c.getId()) + "</id>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<nombre>" + escapeXml((c == null) ? "" : c.getNombre()) + "</nombre>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<email>" + escapeXml((c == null) ? "" : c.getEmail()) + "</email>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<telefono>" + escapeXml((c == null) ? "" : c.getTelefono()) + "</telefono>");
                    bw.newLine();
                    bw.write(INDENTACION3 + "</" + NODO_CLIENTE + ">");
                    bw.newLine();

                    // <habitacion numero=".." tipo="..">
                    String tipo = (h == null || h.getTipo() == null) ? "" : h.getTipo();
                    Integer num = (h == null) ? null : h.getNumero();
                    Boolean disp = (h == null) ? null : h.isDisponible();

                    bw.write(INDENTACION3 + "<" + NODO_HAB
                            + " numero=\"" + ((num == null) ? "" : num) + "\""
                            + " tipo=\"" + escapeXml(tipo) + "\">");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<precioPorNoche>" + d2(precioNoche) + "</precioPorNoche>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<disponible>" + ((disp == null) ? "" : disp) + "</disponible>");
                    bw.newLine();
                    bw.write(INDENTACION3 + "</" + NODO_HAB + ">");
                    bw.newLine();

                    // <fechas>
                    bw.write(INDENTACION3 + "<" + NODO_FECHAS + ">");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<entrada>" + escapeXml(f(e)) + "</entrada>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<salida>" + escapeXml(f(s)) + "</salida>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<noches>" + noches + "</noches>");
                    bw.newLine();
                    bw.write(INDENTACION3 + "</" + NODO_FECHAS + ">");
                    bw.newLine();

                    // <precio>
                    bw.write(INDENTACION3 + "<" + NODO_PRECIO + ">");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<total>" + d2(total) + "</total>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<porNoche>" + d2(precioNoche) + "</porNoche>");
                    bw.newLine();
                    bw.write(INDENTACION3 + "</" + NODO_PRECIO + ">");
                    bw.newLine();

                    // </reserva>
                    bw.write(INDENTACION2 + "</" + NODO_RESERVA + ">");
                    bw.newLine();
                }

                // Cierra <reservas>
                bw.write(INDENTACION + "</" + NODO_RESERVAS + ">");
                bw.newLine();

                // --- <estadisticas> ---
                bw.write(INDENTACION + "<" + NODO_ESTAD + ">");
                bw.newLine();

                // porTipoHabitacion
                bw.write(INDENTACION2 + "<porTipoHabitacion>");
                bw.newLine();
                for (Map.Entry<String, Integer> e : countPorTipo.entrySet()) {
                    String tipo = e.getKey();
                    int cnt = e.getValue();
                    double ing = ingresosPorTipo.getOrDefault(tipo, 0.0);

                    bw.write(INDENTACION3 + "<" + escapeXml(tipo) + ">");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<totalReservas>" + cnt + "</totalReservas>");
                    bw.newLine();
                    bw.write(INDENTACION4 + "<ingresos>" + d2(ing) + "</ingresos>");
                    bw.newLine();
                    bw.write(INDENTACION3 + "</" + escapeXml(tipo) + ">");
                    bw.newLine();
                }
                bw.write(INDENTACION2 + "</porTipoHabitacion>");
                bw.newLine();

                // porEstado
                bw.write(INDENTACION2 + "<porEstado>");
                bw.newLine();
                for (Map.Entry<String, Integer> e : countPorEstado.entrySet()) {
                    String estado = e.getKey();
                    int cnt = e.getValue();
                    bw.write(INDENTACION3 + "<" + escapeXml(estado) + ">" + cnt + "</" + escapeXml(estado) + ">");
                    bw.newLine();
                }
                bw.write(INDENTACION2 + "</porEstado>");
                bw.newLine();

                // resumen
                bw.write(INDENTACION2 + "<resumen>");
                bw.newLine();
                bw.write(INDENTACION3 + "<totalReservas>" + totalReservas + "</totalReservas>");
                bw.newLine();
                bw.write(INDENTACION3 + "<ingresosTotal>" + d2(ingresosTotal) + "</ingresosTotal>");
                bw.newLine();
                bw.write(INDENTACION3 + "<nochesReservadas>" + nochesReservadas + "</nochesReservadas>");
                bw.newLine();
                bw.write(INDENTACION2 + "</resumen>");
                bw.newLine();

                // Cierra </estadisticas> y raíz
                bw.write(INDENTACION + "</" + NODO_ESTAD + ">");
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