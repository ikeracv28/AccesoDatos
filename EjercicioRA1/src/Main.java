import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Cuenta cuenta = new Cuenta(Cliente cliente);


        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos/cuenta.dat"))) {
            oos.writeObject(p);
            System.out.println("Objeto serializado correctamente.");
        } catch (
                IOException e) {
            System.out.println("Error al escribir objeto: " + e.getMessage());
        }



        while(true){
            System.out.println("Indica que quieres hacer:");
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Sacar dinero");
            System.out.println("3. Consultar saldo y listado de movmientos");
            System.out.println("4. Salir");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            switch (opcion){
                case 1:
                    break;
                    case 2:
                        break;
            }
        }
    }
}
