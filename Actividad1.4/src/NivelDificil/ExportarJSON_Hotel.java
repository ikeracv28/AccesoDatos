package NivelDificil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class ExportarJSON_Hotel {

    // === Constantes (idénticas en estilo a tu ExportarJSON base) ===
    static String fecha;
    private static final String ARCHIVO = "json/reservas_";
    private static final String NODOPADRE = "hotel";
    private static final String INDENTACION = "    ";
    private static final String INDENTACION2 = INDENTACION + INDENTACION;
    private static final String INDENTACION3 = INDENTACION2 + INDENTACION;
    private static final String INDENTACION4 = INDENTACION3 + INDENTACION;
    private static final String CARPETA = "json";
    private static final String EXTENSION = ".json";
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String NOMBRE_HOTEL = "Hotel Paradise";

    // === Helpers idénticos ===
    private static String escapeJson(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

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

    public static String crearNombreArchivo() {
        fecha = LocalDateTime.now().format(TS);
        return ARCHIVO + fecha + EXTENSION;
    }

    /** dd/MM/yyyy o vacío si null. */
    private static String f(LocalDate d) {
        return (d == null) ? "" : DF.format(d);
    }

    /** Noches calculadas entre fechas (0 si inválidas). */
    private static int calcNoches(LocalDate entrada, LocalDate salida, int fallback) {
        if (entrada != null && salida != null) {
            long n = ChronoUnit.DAYS.between(entrada, salida);
            return (int) Math.max(0, n);
        }
        return Math.max(0, fallback);
    }

    /** Double → 2 decimales con punto. */
    private static String d2(double v) {
        return String.format(Locale.US, "%.2f", v);
    }

    // === Método principal idéntico en forma al tuyo ===
    public static void escribirJsonExacto(List<Reserva> reservas) {
        try {
            String nombreArchivo = crearNombreArchivo();

            // VALIDACIONES
            if (reservas == null || reservas.isEmpty()) {
                System.out.println("❌ ERROR: No hay productos para exportar.");
                return;
            }
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                System.out.println("❌ ERROR: El nombre del archivo no puede estar vacío.");
                return;
            }

            if (crearCarpeta()) {
                File archivo = new File(nombreArchivo);
                boolean creado = archivo.createNewFile();
                if (!creado) {
                    System.out.println("El archivo no se ha podido crear");
                    return;
                }

                // ==== Cálculos previos ====
                Map<Integer, Cliente> clientes = new LinkedHashMap<>();
                Map<Integer, Habitacion> habitaciones = new LinkedHashMap<>();

                Map<String, Integer> countPorEstado = new LinkedHashMap<>();
                Map<String, Integer> countPorTipo = new LinkedHashMap<>();
                Map<String, Double> ingresosPorTipo = new LinkedHashMap<>();

                int totalReservas = reservas.size();
                long nochesReservadas = 0;
                double ingresosTotal = 0.0;

                for (Reserva r : reservas) {
                    Cliente c = r.getCliente();
                    Habitacion h = r.getHabitacion();

                    if (c != null) clientes.putIfAbsent(c.getId(), c);
                    if (h != null) habitaciones.putIfAbsent(h.getNumero(), h);

                    LocalDate e = r.getFechaEntrada();
                    LocalDate s = r.getFechaSalida();
                    int noches = calcNoches(e, s, r.getNoches());
                    double precioNoche = (h == null) ? 0.0 : h.getPrecioPorNoche();
                    double total = noches * precioNoche;

                    nochesReservadas += noches;
                    ingresosTotal += total;

                    String tipo = (h == null || h.getTipo() == null) ? "Desconocido" : h.getTipo();
                    countPorTipo.merge(tipo, 1, Integer::sum);
                    ingresosPorTipo.merge(tipo, total, Double::sum);

                    String estado = (r.getEstado() == null || r.getEstado().isEmpty()) ? "Desconocido" : r.getEstado();
                    countPorEstado.merge(estado, 1, Integer::sum);
                }

                double ocupacionMedia = (totalReservas == 0) ? 0.0 :
                        Math.round(((double) nochesReservadas / (totalReservas * 10)) * 1000.0) / 10.0;

                // ==== Escritura ====
                try (BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(archivo, true), StandardCharsets.UTF_8))) {

                    bw.write("{");
                    bw.newLine();
                    bw.write(INDENTACION + "\"" + NODOPADRE + "\": {");
                    bw.newLine();

                    // --- informacion ---
                    bw.write(INDENTACION2 + "\"informacion\": {");
                    bw.newLine();
                    bw.write(INDENTACION3 + "\"nombre\": \"" + escapeJson(NOMBRE_HOTEL) + "\",");
                    bw.newLine();
                    bw.write(INDENTACION3 + "\"fecha\": \"" + escapeJson(LocalDate.now().format(DF)) + "\"");
                    bw.newLine();
                    bw.write(INDENTACION2 + "},");
                    bw.newLine();

                    // --- clientes ---
                    bw.write(INDENTACION2 + "\"clientes\": {");
                    bw.newLine();
                    int i = 0, n = clientes.size();
                    for (Cliente c : clientes.values()) {
                        bw.write(INDENTACION3 + "\"" + c.getId() + "\": {");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"nombre\": \"" + escapeJson(c.getNombre()) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"email\": \"" + escapeJson(c.getEmail()) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"telefono\": \"" + escapeJson(c.getTelefono()) + "\"");
                        bw.newLine();
                        bw.write(INDENTACION3 + "}" + (++i < n ? "," : ""));
                        bw.newLine();
                    }
                    bw.write(INDENTACION2 + "},");
                    bw.newLine();

                    // --- habitaciones ---
                    bw.write(INDENTACION2 + "\"habitaciones\": {");
                    bw.newLine();
                    i = 0;
                    n = habitaciones.size();
                    for (Habitacion h : habitaciones.values()) {
                        bw.write(INDENTACION3 + "\"" + h.getNumero() + "\": {");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"tipo\": \"" + escapeJson(h.getTipo()) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"precioPorNoche\": " + d2(h.getPrecioPorNoche()) + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"disponible\": " + h.isDisponible());
                        bw.newLine();
                        bw.write(INDENTACION3 + "}" + (++i < n ? "," : ""));
                        bw.newLine();
                    }
                    bw.write(INDENTACION2 + "},");
                    bw.newLine();

                    // --- reservas ---
                    bw.write(INDENTACION2 + "\"reservas\": [");
                    bw.newLine();
                    i = 0;
                    n = reservas.size();
                    for (Reserva r : reservas) {
                        Cliente c = r.getCliente();
                        Habitacion h = r.getHabitacion();

                        LocalDate e = r.getFechaEntrada();
                        LocalDate s = r.getFechaSalida();
                        int noches = calcNoches(e, s, r.getNoches());
                        double precioNoche = (h == null) ? 0.0 : h.getPrecioPorNoche();
                        double total = noches * precioNoche;

                        bw.write(INDENTACION3 + "{");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"id\": " + r.getId() + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"clienteId\": " + ((c == null) ? 0 : c.getId()) + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"habitacionNumero\": " + ((h == null) ? 0 : h.getNumero()) + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"fechaEntrada\": \"" + escapeJson(f(e)) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"fechaSalida\": \"" + escapeJson(f(s)) + "\",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"noches\": " + noches + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"precioTotal\": " + d2(total) + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + "\"estado\": \"" + escapeJson(r.getEstado()) + "\"");
                        bw.newLine();
                        bw.write(INDENTACION3 + "}" + (++i < n ? "," : ""));
                        bw.newLine();
                    }
                    bw.write(INDENTACION2 + "],");
                    bw.newLine();

                    // --- estadisticas ---
                    bw.write(INDENTACION2 + "\"estadisticas\": {");
                    bw.newLine();

                    // porTipoHabitacion
                    bw.write(INDENTACION3 + "\"porTipoHabitacion\": {");
                    bw.newLine();
                    i = 0;
                    n = countPorTipo.size();
                    for (String tipo : countPorTipo.keySet()) {
                        int totalR = countPorTipo.get(tipo);
                        double ingresos = ingresosPorTipo.getOrDefault(tipo, 0.0);
                        double porcentaje = (ingresosTotal == 0) ? 0.0 : (ingresos / ingresosTotal * 100.0);

                        bw.write(INDENTACION4 + "\"" + escapeJson(tipo) + "\": {");
                        bw.newLine();
                        bw.write(INDENTACION4 + INDENTACION + "\"totalReservas\": " + totalR + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + INDENTACION + "\"ingresos\": " + d2(ingresos) + ",");
                        bw.newLine();
                        bw.write(INDENTACION4 + INDENTACION + "\"porcentaje\": " + String.format(Locale.US, "%.1f", porcentaje));
                        bw.newLine();
                        bw.write(INDENTACION4 + "}" + (++i < n ? "," : ""));
                        bw.newLine();
                    }
                    bw.write(INDENTACION3 + "},");
                    bw.newLine();

                    // porEstado
                    bw.write(INDENTACION3 + "\"porEstado\": {");
                    bw.newLine();
                    i = 0;
                    n = countPorEstado.size();
                    for (String estado : countPorEstado.keySet()) {
                        int cnt = countPorEstado.get(estado);
                        bw.write(INDENTACION4 + "\"" + escapeJson(estado) + "\": " + cnt + (++i < n ? "," : ""));
                        bw.newLine();
                    }
                    bw.write(INDENTACION3 + "},");
                    bw.newLine();

                    // resumen
                    bw.write(INDENTACION3 + "\"resumen\": {");
                    bw.newLine();
                    bw.write(INDENTACION4 + "\"totalReservas\": " + totalReservas + ",");
                    bw.newLine();
                    bw.write(INDENTACION4 + "\"ingresosTotal\": " + d2(ingresosTotal) + ",");
                    bw.newLine();
                    bw.write(INDENTACION4 + "\"nochesReservadas\": " + nochesReservadas + ",");
                    bw.newLine();
                    bw.write(INDENTACION4 + "\"ocupacionMedia\": " + String.format(Locale.US, "%.1f", ocupacionMedia));
                    bw.newLine();
                    bw.write(INDENTACION3 + "}");
                    bw.newLine();

                    bw.write(INDENTACION2 + "}");
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
}