package POJOS;

public class Cliente {
    private String nombre;
    private String DNI;
    private String nCuenta;
    private double saldo;


    public Cliente(String nombre, String DNI, String nCuenta) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.nCuenta = nCuenta;
        this.saldo = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public String getnCuenta() {
        return nCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo += saldo;
    }
}
