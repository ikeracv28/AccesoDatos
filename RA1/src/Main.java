import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        Cuenta cuenta = new Cuenta();
        cuenta = cuenta.cargarCuenta();
        Menu menu = new Menu();


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
                        cuenta.guardarCuenta();
                        System.out.println("Se ha guardado la cuenta, muchas gracias!!!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Elige una opcion valida del menu");

                }

            } else {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next();
            }

        }
    }
}
