import RA2_Exportaciones.ExportarCSV;
import RA2_Exportaciones.ExportarJson;
import RA2_Exportaciones.ExportarXML;
import ClasesRA1.Cliente;
import ClasesRA1.Cuenta;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static RA2_Exportaciones.ExportarJson.escribirJsonExacto;
import static RA2_Exportaciones.ExportarXML.escribirXmlExacto;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        Cuenta cuenta = new Cuenta();
        Cliente cliente;
        Menu menu = new Menu();
        ExportarCSV exportarCSV = new ExportarCSV();
        ExportarXML exportarXML = new ExportarXML();
        ExportarJson exportarJson = new ExportarJson();
        crearCarpeta();

        if ((cuenta = iniciarCuenta(cuenta.cargarCuenta())) != null) {
            while (true) {
                menu.menuBanco();
                System.out.println("Seleccione una opción: ");
                if (sc.hasNextInt()) {
                    int eleccion = sc.nextInt();
                    switch (eleccion) {
                        case 1:
                            cuenta.ingresarDinero();
                            break;
                        case 2:
                            cuenta.retirarDinero();
                            break;
                        case 3:
                            cuenta.consulatarSaldoYMovimientos();
                            break;

                        case 4:
                            exportarCSV.exportarCSV(cuenta);
                            break;

                        case 5:
                            escribirXmlExacto(cuenta);
                            break;

                        case 6:
                            escribirJsonExacto(cuenta);
                            break;

                        case 7:
                            cuenta.guardarCuenta();
                            System.out.println("Se ha guardado la cuenta, muchas gracias!!!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Elige una opcion valida del menu");

                    }

                } else {
                    System.out.println("Por favor, ingrese un número válido");
                    sc.next();
                }

            }
        }

        }
    static public void crearCarpeta(){
        try{
            // Creamos la carpeta "datos" si no existe
            File carpeta = new File("datos");
            if (!carpeta.exists()) {
                carpeta.mkdir(); // crea el directorio
            }
            File archivo = new File("datos/cuenta.dat");
            if (!archivo.exists()) {
                archivo.createNewFile(); // crea el directorio
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }

    static public Cuenta iniciarCuenta(Cuenta cuenta){
        Scanner sc = new Scanner(System.in);

        try {
            if (cuenta == null || cuenta.getCliente() == null) {
                System.out.println("Dime un nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Dime un DNI para la cuenta: ");
                String dni = sc.nextLine();
                System.out.println("Dime un Número de cuenta: ");
                String Ncuenta = sc.nextLine();

                // Crear un nuevo cliente
                Cliente cliente = new Cliente(nombre, dni, Ncuenta);

                // Asignar el cliente a la cuenta
                cuenta.setCliente(cliente);

                return cuenta;


            } else {
                // Si el cliente ya está inicializado, solo devolvemos true
                return cuenta;
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar la cuenta: " + e.getMessage());
        }
        return null;
    }



}
