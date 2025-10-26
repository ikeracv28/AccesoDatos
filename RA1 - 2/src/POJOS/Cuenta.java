package POJOS;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    private double saldo;
    private ArrayList<Movimiento> movimientos;
    private Cliente cliente;
    static Scanner sc = new Scanner(System.in);

    public Cuenta() {
        this.saldo = 0;
        this.movimientos = new ArrayList<>();

    }


    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cuenta(double saldo, Cliente cliente) {
        this.saldo = saldo;
        this.movimientos = new ArrayList<>();
        this.cliente = cliente;
    }

    public Cuenta(double saldo) {

        this.saldo = saldo;
        this.movimientos = new ArrayList<>();
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
            sc.nextLine();
            if (dineroIngresar > 0) {
                System.out.println("Dime el concepto: ");
                String concepto = sc.nextLine();
                sc.nextLine();
                movimientos.add(new Movimiento("Ingreso", dineroIngresar, concepto));
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
            sc.nextLine();
            if(dineroRetirar <= 0){
                System.out.println("La cantidad que vas a retirar tiene que ser mayor a 0");
            } else if(dineroRetirar > saldo){
                System.out.println("No puedes retirar mas dinero del que tienes");
            }else{
                System.out.println("Dime el concepto: ");
                String concepto = sc.nextLine();
                movimientos.add(new Movimiento("Retirada", dineroRetirar, concepto));
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
        // Verificar si el cliente está inicializado antes de guardar
        if (this.cliente == null) {
            System.out.println("Cliente no inicializado, asignando un cliente por defecto.");
            this.cliente = new Cliente("Nombre por defecto", "DNI0000", "00000"); // Cliente por defecto
        }

        // Imprime el cliente antes de guardar
        System.out.println("Guardando cliente: " + this.cliente.getNombre());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos/cuenta.dat"))) {
            oos.writeObject(this);
            System.out.println("Cuenta guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta: " + e.getMessage());
        }
    }


    public Cuenta cargarCuenta() {
        File archivo = new File("datos/cuenta.dat");

        if (!archivo.exists()) {
            System.out.println("No existe el archivo, se creará una nueva cuenta.");
            return new Cuenta();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Cuenta cuentaCargada = (Cuenta) ois.readObject();

            // Verificar si el cliente está null y mostrar información
            if (cuentaCargada.getCliente() == null) {
                System.out.println("El cliente no estaba inicializado. Creando nuevo cliente.");
                cuentaCargada.setCliente(new Cliente("Nuevo Cliente", "DNI0000", "00000"));
            }

            System.out.println("Cuenta cargada correctamente con cliente: " + cuentaCargada.getCliente().getNombre());
            return cuentaCargada;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la cuenta: " + e.getMessage());
            return new Cuenta();
        }
    }

}

