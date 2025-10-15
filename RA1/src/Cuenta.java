import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Cuenta implements Serializable {
    private double saldo;
    private ArrayList<Movimiento> movimientos;
    private Cliente cliente;
    static Scanner sc = new Scanner(System.in);

    public Cuenta() {
        this.saldo = 0;
        this.movimientos = new ArrayList<>();
    }

    public Cuenta(double saldo) {

        this.saldo = saldo;
        this.movimientos = new ArrayList<>();
    }

    public double getSaldo() {

        double saldo = 0;
        for(Movimiento movimiento :movimientos) {
            // suma la cantidad de cada movimiento al saldo
            saldo += movimiento.getCantidad();
        }
        return saldo;

    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }



    // metodo para ingresar dinero
    public void ingresarDinero(){
        System.out.println("Dime cuanto dinero quieres ingresar: ");
        if (sc.hasNextDouble()) {
            double dineroIngresar = sc.nextDouble();
            if (dineroIngresar > 0) {
                movimientos.add(new Movimiento("Ingreso", dineroIngresar));
                saldo += dineroIngresar;
            } else {
                System.out.println("La cantidad tiene que ser positiva");
            }
        }else {
            System.out.println("Por favor, introduce un número valido");
            sc.nextLine();

        }

    }

    // metodo para retirar dinero
    public void retirarDinero(){
        System.out.println("Dime cuanto dinero quieres retirar: ");
        if(sc.hasNextDouble()){
            double dineroRetirar = sc.nextDouble();
            if(dineroRetirar <= 0){
                System.out.println("La cantidad que vas a retirar tiene que ser mayor a 0");
            } else if(dineroRetirar > saldo){
                System.out.println("No puedes retirar mas dinero del que tienes");
            }else{
                movimientos.add(new Movimiento("Retirada", dineroRetirar));
                saldo -= dineroRetirar;
            }
        }else {
            System.out.println("Por favor, introduce un número valido");
            sc.nextLine();
        }
    }

    public void consulatarSaldoYMovimientos(){
        try{
           double saldoActual = getSaldo();
            System.out.println("Tu saldo es de: " + saldoActual);
           for(Movimiento movimiento :movimientos){
               System.out.println(movimiento.toString());
           }

        }catch(NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void guardarCuenta() {
        // Creamos la carpeta "datos" si no existe
        File carpeta = new File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir(); // crea el directorio
        }

        // Usamos try-with-resources para cerrar el flujo automáticamente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos/cuenta.dat"))) {
            // "this" se refiere al propio objeto Cuenta actual que queremos guardar
            oos.writeObject(this);
            System.out.println("Cuenta guardada correctamente.");
        } catch (IOException e) {
            // Capturamos cualquier error de escritura
            System.out.println("Error al guardar la cuenta: " + e.getMessage());
        }
    }

    // Carga una cuenta desde el archivo binario "cuenta.dat"
    public Cuenta cargarCuenta() {
        File archivo = new File("datos/cuenta.dat");

        if (!archivo.exists()) {
            System.out.println("No existe el archivo, se creará una nueva cuenta.");
            return new Cuenta(); // devolvemos cuenta vacía
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            // Leemos el objeto y lo casteamos a Cuenta
            Cuenta cuentaCargada = (Cuenta) ois.readObject();
            System.out.println("Cuenta cargada correctamente.");
            return cuentaCargada;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la cuenta: " + e.getMessage());
            return new Cuenta(); // si hay error, devolvemos cuenta vacía
        }
    }

    }

