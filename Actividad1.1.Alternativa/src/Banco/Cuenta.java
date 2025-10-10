package Banco;
import POJOS.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cuenta {
    Cliente cliente = new Cliente();
    Movimientos movimiento;
    Scanner sc = new Scanner(System.in);

    public void comprobarArchivos()  {
        try{
            //comprobcion de creacion de carpeta ddatos
            File directorio = new File("datos");
            if(!directorio.exists()){
                directorio.mkdir();
            }

            //comprobcion de fichero titular
            File ficheroT = new File("datos/titular.txt");
            if (!ficheroT.exists()) {
                ficheroT.createNewFile();
            }

            // comprobcion de fichero saldo
            File ficheroS = new File("datos/saldo.txt");
            if (!ficheroS.exists()) {
                ficheroS.createNewFile();
                double setSaldo = 0;
                BufferedWriter escribirSaldo = new BufferedWriter(new FileWriter("datos/saldo.txt"));
                escribirSaldo.write(String.valueOf(setSaldo));
                escribirSaldo.flush();
            }

            //comprobcion de fichero movimiento
            File ficheroM = new File("datos/movimiento.txt");
            if (!ficheroM.exists()) {
                ficheroM.createNewFile();
            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void comprobarCuenta() {
        try (BufferedReader leerTitular = new BufferedReader(new FileReader("datos/titular.txt"));
             BufferedWriter escribirTitular = new BufferedWriter(new FileWriter("datos/titular.txt", true));
             BufferedWriter escribirSaldo = new BufferedWriter(new FileWriter("datos/saldo.txt"))) {

            // si el archivo esta vacio, le pedimos sus datos
            if (leerTitular.readLine() == null) {
                System.out.println("Dime tu nombre: ");
                String nombre = sc.nextLine();

                System.out.println("Dime tu DNI: ");
                String DNI = sc.nextLine();

                System.out.println("Dime tu numero de cuenta: ");
                String nCuenta = sc.nextLine();

                // creamos el primer cliente con sus datos
                cliente = new Cliente(nombre, DNI, nCuenta);
                escribirTitular.write(cliente.getNombre());
                escribirTitular.newLine();
                escribirTitular.write(cliente.getDNI());
                escribirTitular.newLine();
                escribirTitular.write(cliente.getnCuenta());
                escribirTitular.newLine();
                escribirTitular.flush();

                /*
                escribirSaldo.write(String.valueOf(cliente.getSaldo()));
                escribirSaldo.flush();
                */

            }else {
                BufferedReader leerTitular1 = new BufferedReader(new FileReader("datos/titular.txt"));
                String nombre =  leerTitular1.readLine();
                String DNI = leerTitular1.readLine();
                String nCuenta = leerTitular1.readLine();
                //Cliente cliente = new Cliente(nombre, DNI, nCuenta);
                // para mostrarlo como pide el ejercicio por consola
                System.out.println(nombre);
                System.out.println(DNI);
                System.out.println(nCuenta);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // con este metodo se registra
    public void registrarMovimientos(boolean tipob, String cantidad, String fecha, String concepto) {
        try (BufferedWriter escribirMovimientos = new BufferedWriter(new FileWriter("datos/movimiento.txt", true))) {
            // esto simplemente es para poner la fecha en el formto que se pide
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            //movimiento = new Movimientos(movimiento.getTipo(), movimiento.getCantidad(), movimiento.getFecha(), movimiento.getConcepto());

            // esto lo hago para que si pasamos parametro true sea ingreso y si es false que sea retirada
            String tipo = tipob ? "INGRESO" : "RETIRADA";
            // y aqui registramos el movimiento
            escribirMovimientos.write(tipo + ";");
            escribirMovimientos.write((cantidad) + ";");
            escribirMovimientos.write(formato.format(fechaActual) + ";");
            escribirMovimientos.write(concepto);
            escribirMovimientos.newLine();
            escribirMovimientos.flush();
            System.out.println("Se ha registrado el movimiento...");

        } catch (IOException e) {
            System.out.println("No se ha escrito en el movimiento");
            throw new RuntimeException(e);
        }
    }

    // COn este metoddo vamos a mostrar el movimiento
    public void mostrarMovimientos(){
        try(BufferedReader leerMovimiento = new BufferedReader(new FileReader("datos/movimiento.txt"))){

            if (leerMovimiento.readLine() == null) {
                System.out.println("no hay movimientos de momento");

            }else{
                String linea = leerMovimiento.readLine();

                String tipo =  linea.split(";")[0];
                boolean comprobacion = tipo.equals("INGRESO") ? true : false;
                String cantidad = linea.split(";")[1];
                String fecha = linea.split(";")[2];
                String concepto = linea.split(";")[3];

                //Movimientos movimientos = new Movimientos(comprobacion, Double.parseDouble(cantidad), fecha, concepto);

                // para mostrarlo como pide el ejercicio por consola
                System.out.println(tipo);
                System.out.println(cantidad);
                System.out.println(fecha);
                System.out.println(concepto);
            }
        }catch (IOException e){

        }
    }


    public void registrarSaldo(double saldo){
        try(BufferedWriter escribirSaldo = new BufferedWriter(new FileWriter("datos/saldo.txt"))){
            escribirSaldo.write(String.valueOf(saldo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // metodo para ingresar dinero
    public void ingresarDinero(){
        try{
            cliente = new Cliente();

            // esto simplemente es para poner la fecha en el formto que se pide
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            String dineroIngresar = "";

            System.out.println("Dime cuanto dinero quieres ingresar: ");
            dineroIngresar = sc.nextLine();
            dineroIngresar = dineroIngresar.replace(',', '.'); // Reemplaza el punto por la coma

            cliente.setSaldo(cliente.getSaldo() + Double.parseDouble(dineroIngresar));
            System.out.println("Dime el concepto de esta transaccion");
            String concepto = sc.nextLine();
            String fecha = formato.format(fechaActual);
            registrarMovimientos(true, dineroIngresar, fecha, concepto);
            registrarSaldo(cliente.getSaldo());

        }catch(NumberFormatException e) {
            System.out.println("Ingreso no valido");
        }
    }

    // metodo para retirar dinero
    public void retirarDinero(){
        try {
            // esto simplemente es para poner la fecha en el formto que se pide
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");


            while(true){
            System.out.println("Dime cuanto dinero quieres retirar: ");
            double dineroRetirar = sc.nextDouble();
            if(dineroRetirar <= cliente.getSaldo()){
                cliente.setSaldo(-dineroRetirar);
                System.out.println("Dime el concepto de esta transaccion");
                String concepto = sc.nextLine();
                String fecha = formato.format(fechaActual);
                registrarMovimientos(false, String.valueOf(dineroRetirar) ,fecha, concepto );
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
        try(BufferedReader leerSaldo = new BufferedReader(new FileReader("datos/saldo.txt"))){
            if (leerSaldo.readLine() == null) {
                double setSaldo = 0;
                BufferedWriter escribirSaldo = new BufferedWriter(new FileWriter("datos/saldo.txt"));
                escribirSaldo.write(String.valueOf(setSaldo));
                escribirSaldo.flush();
                System.out.println(leerSaldo.readLine());
            }else{
                System.out.println(leerSaldo.readLine());
            }
        }catch(NumberFormatException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

