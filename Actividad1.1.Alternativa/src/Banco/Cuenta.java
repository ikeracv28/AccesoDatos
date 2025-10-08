package Banco;
import POJOS.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cuenta {
    Cliente cliente;
    Movimientos movimiento;
    Scanner sc = new Scanner(System.in);

    // los creamos aqui para poder utilizarlos en todos los metodos
    BufferedWriter escribirTitular;
    BufferedWriter escribirSaldo;
    BufferedWriter escribirMovimientos;

    BufferedReader leerTitular;
    BufferedReader leerSaldo;
    BufferedReader leerMovimientos;

    public Cuenta(){
        try{
        // para crear archivos, el append true, es para añadirlo al final del archivo en vez de sobreescribir y que me lo borre
            escribirTitular = new BufferedWriter(new FileWriter("titular.txt", true));
            escribirSaldo = new BufferedWriter(new FileWriter("saldo.txt"));
            escribirMovimientos = new BufferedWriter(new FileWriter("movimientos.txt", true));

            // para leer los archivos
            leerTitular = new BufferedReader(new FileReader("datos/titular.txt"));
            leerSaldo = new BufferedReader(new FileReader("datos/saldo.txt"));
            leerMovimientos = new BufferedReader(new FileReader("datos/movimientos.txt"));

        } catch (IOException e) {
            System.out.println("Error al crear archivos");
        }
    }

    public void comprobarCuenta() {
        try{
            // si el archivo esta vacio, le pedimos sus datos
            if(leerTitular.readLine() == null){
                System.out.println("Dime tu nombre: ");
                String nombre = sc.nextLine();

                System.out.println("Dime tu DNI: ");
                String DNI = sc.nextLine();

                System.out.println("Dime tu numero de cuenta: ");
                String nCuenta = sc.nextLine();

                // creamos el primer cliente con sus datos
                cliente = new Cliente(nombre,DNI,nCuenta);
                escribirTitular.write(cliente.getNombre());
                escribirTitular.write("\n");
                escribirTitular.write(cliente.getDNI());
                escribirTitular.write("\n");
                escribirTitular.write(cliente.getnCuenta());
                escribirSaldo.write(String.valueOf(cliente.getSaldo()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registrarMovimientos(Movimientos movimiento){
        try{
            // esto simplemente es para poner la fecha en el formto que se pide
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            // esto lo hago paraa que si paasamos parametro true sea ingreso y si es false que sea retirada
            String tipo;
            if(movimiento.getTipo()== true){
                tipo = "INGRESO";
            }else {
                tipo = "RETIRADA";
            }
            // y aqui registramos el movimiento
            escribirMovimientos.write(tipo + ";");
            escribirMovimientos.write(String.valueOf(movimiento.getCantidad()));
            escribirMovimientos.write(formato.format(fechaActual) + ";");
            escribirMovimientos.write(movimiento.getConcepto());

            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registrarSaldo(double saldo){
        try{
            escribirSaldo.write(String.valueOf(saldo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // metodo para ingresar dinero
    public void ingresarDinero(){
        try{
            System.out.println("Dime cuanto dinero quieres ingresar: ");
            double dineroIngresar = sc.nextDouble();
            sc.nextLine();
            cliente.setSaldo(cliente.getSaldo() + dineroIngresar);
            System.out.println("Dime el concepto de esta transaccion");
            String concepto = sc.nextLine();
            registrarMovimientos(new Movimientos(true, dineroIngresar, concepto));
            registrarSaldo(cliente.getSaldo());

        }catch(NumberFormatException e) {
            System.out.println("Ingreso no valido");

        }
    }
    // metodo para retirar dinero
    public void retirarDinero(){
        try {
            while(true){
            System.out.println("Dime cuanto dinero quieres retirar: ");
            double dineroRetirar = sc.nextDouble();
            if(dineroRetirar <= cliente.getSaldo()){
                cliente.setSaldo(-dineroRetirar);
                System.out.println("Dime el concepto de esta transaccion");
                String concepto = sc.nextLine();
                registrarMovimientos(new Movimientos(false, dineroRetirar, concepto));
                registrarSaldo(cliente.getSaldo());
                break;
            }else {
                System.out.println("No puede retirar mas dinero del que tienes");
            }
            }
        }catch (NumberFormatException e) {
            System.out.println("Retirada no valido");
        }
    }

    public void consultarSaldo(){
        try{
            System.out.println(leerSaldo.readLine());
        }catch(NumberFormatException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

