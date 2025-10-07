import java.io.Serializable;
import java.util.Scanner;

public class Cliente implements Serializable {
    String nombre;
    String DNI;
    int edad;
    double saldo;
    Scanner sc = new Scanner(System.in);

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public int getEdad() {
        return edad;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente(String nombre, String DNI, int edad, double saldo) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.edad = edad;
        this.saldo = saldo;
    }


    public void actualizarSaldo(double saldo){
        this.saldo += saldo;
    }

}
