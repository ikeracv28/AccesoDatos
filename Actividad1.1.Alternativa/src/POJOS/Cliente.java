package POJOS;

public class Cliente {
    private String nombre;
    private String DNI;
    private String nCuenta;
    private double saldo;


    public Cliente() {
        this.saldo = 0;
    }

    public Cliente(String nombre, String DNI, String nCuenta) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.nCuenta = nCuenta;
        this.saldo = 0;
    }
    public Cliente(String nombre, String DNI, String nCuenta, double saldo) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.nCuenta = nCuenta;
        this.saldo = saldo;
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

    public void actualizarSaldo(double saldo) {
        this.saldo += saldo;

    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;

    }

}
