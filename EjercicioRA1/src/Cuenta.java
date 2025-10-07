import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Cuenta implements Serializable {
    ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
    Cliente cliente;
    Scanner sc = new Scanner(System.in);


    public Cuenta(Cliente cliente) throws IOException {
        this.cliente = cliente;

    }

    public void ingresarDinero() {
        try {
            System.out.println("Cuanto dinero quieres ingresar: ");
            double ingreso = sc.nextDouble();
            sc.nextLine();
            cliente.actualizarSaldo(ingreso);
            movimientos.add(new Movimiento(ingreso));
            System.out.println("Dinero ingresado: " + ingreso);
        } catch (Exception e) {
            System.out.println("Error al ingresar el dinero");
        }
    }

        public void retirarDinero() {
            try {
                System.out.println("Cuanto dinero quieres sacar: ");
                double retirada = sc.nextDouble();
                sc.nextLine();
                cliente.actualizarSaldo(-1* retirada);
                movimientos.add(new Movimiento(retirada));
                System.out.println("Dinero retirado: " + retirada);
            }catch (Exception e){
                System.out.println("Error al retirar el dinero");
            }
    }


}



