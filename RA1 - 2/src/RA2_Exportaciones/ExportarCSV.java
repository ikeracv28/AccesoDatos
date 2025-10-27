package RA2_Exportaciones;

import ClasesRA1.Cliente;
import ClasesRA1.Cuenta;
import ClasesRA1.Movimiento;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class ExportarCSV {

    final String archivo = "csv/cuenta";
    static Cuenta cuenta = null ;
    private static final String separador = ";";
    private static final String CARPETA = "csv"; // NOMBRE CARPETA

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

    private static String escaparCSV(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        // Si contiene el separador, comillas o saltos de línea, debemos escapar
        if (texto.contains(separador) || texto.contains("\"") || texto.contains("\n")) {
            // Duplicamos las comillas y encerramos todo entre comillas
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }

        return texto;
    }

    public void exportarCSV(Cuenta cuenta) {

        // Verificamos si el cliente está null antes de intentar acceder a su nombre
        if (cuenta.getCliente() == null) {
            System.out.println("Error: Cliente no inicializado.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String nombreArchivo = archivo + timestamp + ".csv";
        Cliente cliente = cuenta.getCliente();
        ArrayList<Movimiento> movimientos =  cuenta.getMovimientos();


        if (cuenta == null ) {
            System.out.println("ERROR: No hay cuentas para exportar.");
            return;
        }


        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            System.out.println("ERROR: El nombre del archivo no puede estar vacío.");
            return;
        }

        if(crearCarpeta()){
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(nombreArchivo))) {
                String headerCliente = String.join(separador, "Nombre", "DNI", "NumeroCuenta", "Saldo");
                bf.write(headerCliente);
                bf.newLine();

                String lineaCliente;
                lineaCliente = escaparCSV(cliente.getNombre()) + separador + escaparCSV(cliente.getDNI())  + separador + escaparCSV(cliente.getnCuenta())  + separador + cuenta.getSaldo() ;
                bf.write(lineaCliente);
                bf.newLine();
                bf.newLine();
                bf.newLine();



                String headerMovimientos = String.join(separador, "Cantidad", "Tipo", "Concepto");
                bf.write(headerMovimientos);
                bf.newLine();

                String lineaMovimientos;
                for (Movimiento mov : movimientos) {

                    lineaMovimientos = mov.getCantidad()+ separador + escaparCSV(mov.getTipo()) + separador + escaparCSV(mov.getConcepto()) + separador;
                    bf.write(lineaMovimientos);
                    bf.newLine();

                }

                escribirResumen(bf, movimientos);


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("El archivo se generara cuando cierres el programa");

    }

    // Escribe el Resumen de los movimientos
    private static void escribirResumen(BufferedWriter writer, ArrayList<Movimiento> movimientos) throws IOException {
        // Cálculos

        double totalGastado = 0;
        double totalIngresado = 0;
        for (Movimiento mov : movimientos) {
            if (mov.getTipo().equalsIgnoreCase("ingreso")) {
                totalIngresado += mov.getCantidad();
            } else {
                totalGastado -= mov.getCantidad();
            }
        }


        writer.newLine(); // Línea en blanco (Para separar el resumen de los elementos de arriba)

        writer.write("# RESUMEN MOVIMIENTOS");
        writer.newLine();
        writer.write("# Total movimientos" + separador + movimientos.size());
        writer.newLine();
        writer.write("# Total gastos" + separador + String.format("%.2f", totalGastado));
        writer.newLine();
        writer.write("# Total ingresos" + separador + String.format("%.2f", totalIngresado));
        writer.newLine();
    }
}

