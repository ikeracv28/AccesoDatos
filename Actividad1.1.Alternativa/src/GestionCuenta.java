import Banco.Cuenta;
import POJOS.Cliente;
import POJOS.Movimientos;

import java.util.Scanner;

public class GestionCuenta {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Cuenta cuenta = new Cuenta();
        Cliente cliente = new Cliente();
        Movimientos movimientos = new Movimientos();

    // lo primero que haces es comprobar que existen los archivos
        cuenta.comprobarArchivos();
        System.out.println("Bienvenido al banco, eligue una opcion del menu");
        while(true){
            menu.menuBanco();
            System.out.println("Seleccione una opción: ");
            int eleccion = sc.nextInt();

            switch(eleccion){
                case 1:
                    cuenta.consultarSaldo();
                    break;
                case 2:
                    cuenta.comprobarCuenta();
                    break;
                case 3:
                    cuenta.mostrarMovimientos();
                    break;
                case 4:
                    cuenta.ingresarDinero();
                    break;
                case 5:
                    cuenta.retirarDinero();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        }
    }
}
