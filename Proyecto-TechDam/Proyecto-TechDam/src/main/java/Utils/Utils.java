package utils;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;


public class Utils {
    public final static Scanner sc = new Scanner(System.in);

    // CONTROL DE ENTRADA DE USUARIO:

    // (evita recibir números en cadenas de texto o cadenas vacías)
    public static String leerString() {
        try {
            String stringEntrada = sc.nextLine().trim();

            // Validación para evitar cadenas vacías
            if (stringEntrada.isEmpty()) {
                System.out.print("ERROR: El texto no puede estar vacío. Vuelve a intentarlo: ");
                return leerString(); // Llamada recursiva para pedir la entrada nuevamente
            }

            // Validación para evitar números en el texto
            if (stringEntrada.matches(".*\\d.*")) { // Verifica si el texto contiene números
                System.out.print("ERROR: El texto no puede contener números. Vuelve a intentarlo: ");
                return leerString(); // Llamada recursiva para pedir la entrada nuevamente
            }

            // Si no contiene números, se devuelve el string
            return stringEntrada;
        } catch (Exception e) {
            System.out.print("Error al leer el texto. Inténtalo de nuevo: ");
            return leerString();
        }
    }

    public static int leerEntero() {
        try {
            int num = sc.nextInt();
            sc.nextLine(); // Limpiar buffer
            return num;
        } catch (InputMismatchException e) {
            sc.nextLine(); // Limpiar buffer
            System.out.print("Por favor, introduce un número entero: ");
            return leerEntero();
        }
    }

    public static double leerDouble() {
        try {
            double num = sc.nextDouble();
            sc.nextLine(); // Limpiar buffer
            return num;
        } catch (InputMismatchException e) {
            sc.nextDouble(); // Limpiar buffer
            System.out.print("Por favor, introduce un número: ");
            return leerDouble();
        }
    }

    public static BigDecimal leerBigDecimal() {
        try {
            BigDecimal num = sc.nextBigDecimal();
            sc.nextLine(); // Limpiar buffer
            return num;
        } catch (InputMismatchException e) {
            sc.nextLine(); // Limpiar buffer
            System.out.print("Por favor, introduce un número válido: ");
            return leerBigDecimal();
        }
    }

    public static LocalDate leerFecha() {
        try {
            String fechaRecibida = sc.nextLine().trim();

            // Se intenta parsear la fecha con el formato "yyyy-MM-dd"
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(fechaRecibida, formato); // Se convierte la cadena a LocalDate
            return fecha;
        } catch (DateTimeParseException e) {
            // Si la fecha no es válida, se muestra el error y se vuelve a pedir la entrada
            System.out.print("Fecha no válida. Introduce una fecha válida (formato: Año-Mes-Día): ");
            return leerFecha();
        }
    }

    public static double leerCantidadPositiva() {
        double cant = leerDouble();
        while (cant <= 0) {
            System.out.print("Por favor, introduce una cantidad positiva: ");
            cant = leerDouble();
        }
        return cant;
    }

    public static boolean preguntaSiNo() {
        System.out.println("1. Sí");
        System.out.println("2. No");
        int opcion = leerOpcion();
        if (opcion == 1) {
            return true;
        } else if (opcion == 2) {
            return false;
        } else {
            System.out.print("Opción no válida. Por favor, introduce 1 (Sí) o 2 (No): ");
            return preguntaSiNo();
        }
    }

//    // Verifica si el DNI obtenido contiene 8 números y 1 letra
//    public static String verificarDNI(String DNI) {
//        while (DNI == null | !DNI.matches("\\d{8}[A-Za-z]")) {
//            System.out.println("DNI incorrecto. El DNI debe contener 8 números y 1 letra.");
//            System.out.print("Vuelve a introducir el DNI: ");
//            DNI = sc.nextLine().trim();
//        }
//        return DNI.toUpperCase();
//    }


    // PARA MENÚS:

    public static int leerOpcion() {
        System.out.print("↳ Seleccione una opción: ");
        return leerEntero();
    }

    // (Pausa el menú hasta que el usuario presione ENTER)
    public static void pausar() {
        System.out.print("\nPresiona ENTER para continuar...");
        sc.nextLine();
    }

    public static void opcionInvalida() {
        System.out.println("Opción inválida. Introduce el número correspondiente a la opción que quieras seleccionar.");
    }




}