package Modelo;

public class Venta {

    private int id_venta;
    private double precio;
    private int id_pedido;

    public Venta() {}

    public Venta(int id_venta, double precio, int id_pedido) {
        this.id_venta = id_venta;
        this.precio = precio;
        this.id_pedido = id_pedido;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
}
