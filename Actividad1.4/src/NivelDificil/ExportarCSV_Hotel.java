package NivelDificil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;


public class ExportarCSV_Hotel {

    // === Config y formato de salida (siguiendo tu estilo del nivel básico) ===
    private static final String CARPETA = "csv";                  // carpeta de salida
    private static final String SEPARADOR = ";";                  // separador CSV
    private static final String PREFIJO_ARCHIVO = "csv/reservas_";
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formato de fechas
    private static final String EXT = ".csv";

    // === Utilidades fieles a tu patrón ===
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

    private static String escaparCSV(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        if (texto.contains(SEPARADOR) || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }

    private static String crearNombreArchivo() {
        String ts = LocalDateTime.now().format(TS);
        return PREFIJO_ARCHIVO + ts + EXT;
    }

    /**
     * Exporta las reservas a CSV "aplanando" las relaciones y calculando campos.
     *
     * @param reservas lista de reservas a exportar (no nula ni vacía)
     */
    public void exportarCSV(List<Reserva> reservas) {
        // Validaciones idénticas a tu estilo
        if (reservas == null || reservas.isEmpty()) {
            System.out.println("ERROR: No hay reservas para exportar.");
            return;
        }
        String nombreArchivo = crearNombreArchivo();
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
            return;
        }

        // Orden estable de salida: por fecha de entrada y luego por id
        reservas.sort(Comparator
                .comparing(Reserva::getFechaEntrada, Comparator.nullsLast(LocalDate::compareTo))
                .thenComparing(Reserva::getId));

        if (!crearCarpeta()) {
            System.out.println("ERROR: No se pudo crear/acceder a la carpeta de salida.");
            return;
        }

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Cabecera
            String header = String.join(SEPARADOR,
                    "ID",
                    "ClienteNombre",
                    "ClienteEmail",
                    "HabitacionNum",
                    "TipoHabitacion",
                    "FechaEntrada",
                    "FechaSalida",
                    "Noches",
                    "PrecioTotal",
                    "Estado");
            bf.write(header);
            bf.newLine();

            // Filas
            for (Reserva r : reservas) {
                Cliente c = r.getCliente();
                Habitacion h = r.getHabitacion();

                // Datos base (con null-safe)
                String clienteNombre = (c == null) ? "" : c.getNombre();
                String clienteEmail  = (c == null) ? "" : c.getEmail();
                Integer numHab       = (h == null) ? null : h.getNumero();
                String tipoHab       = (h == null) ? "" : h.getTipo();
                double precioNoche   = (h == null) ? 0.0 : h.getPrecioPorNoche();

                LocalDate entrada = r.getFechaEntrada();
                LocalDate salida  = r.getFechaSalida();

                // Cálculos:
                // noches calculadas por fechas; si alguna fecha es null, usa el valor de la reserva
                int nochesCalc = (entrada != null && salida != null)
                        ? (int) ChronoUnit.DAYS.between(entrada, salida)
                        : r.getNoches();
                if (nochesCalc < 0) nochesCalc = 0; // seguridad

                // precio total calculado (independiente del valor existente)
                double precioTotalCalc = nochesCalc * precioNoche;

                // Formateos
                String fechaEntradaStr = (entrada == null) ? "" : DF.format(entrada);
                String fechaSalidaStr  = (salida == null) ? "" : DF.format(salida);

                String linea = String.join(SEPARADOR,
                        String.valueOf(r.getId()),
                        escaparCSV(clienteNombre),
                        escaparCSV(clienteEmail),
                        (numHab == null) ? "" : String.valueOf(numHab),
                        escaparCSV(tipoHab),
                        fechaEntradaStr,
                        fechaSalidaStr,
                        String.valueOf(nochesCalc),
                        String.format("%.2f", precioTotalCalc).replace(",", "."),
                        escaparCSV(r.getEstado())
                );

                bf.write(linea);
                bf.newLine();
            }

            // (Opcional) Línea resumen simple: total reservas e ingresos
            double ingresosTotales = reservas.stream().mapToDouble(res -> {
                Habitacion h = res.getHabitacion();
                double precioNoche = (h == null) ? 0.0 : h.getPrecioPorNoche();
                LocalDate e = res.getFechaEntrada();
                LocalDate s = res.getFechaSalida();
                int noches = (e != null && s != null) ? (int) ChronoUnit.DAYS.between(e, s) : res.getNoches();
                if (noches < 0) noches = 0;
                return noches * precioNoche;
            }).sum();

            bf.newLine();
            bf.write("# TotalReservas;" + reservas.size());
            bf.newLine();
            bf.write("# IngresosTotales;" + String.format("%.2f", ingresosTotales).replace(",", "."));
            bf.newLine();

            System.out.println("CSV exportado correctamente: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}