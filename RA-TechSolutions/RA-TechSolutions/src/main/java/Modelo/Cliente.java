package Modelo;

public class Cliente {
    private int id_cliente;
    private String nombre;
    private int edad;
    private int id_venta;

    public Cliente(){}

    public Cliente(int id_cliente, String nombre, int edad, int id_venta) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.edad = edad;
        this.id_venta = id_venta;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}
