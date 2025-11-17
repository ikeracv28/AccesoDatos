package Modelo;

public class Pedido {
    private int id_pedido;
    private int cantidad;
    private int id_cliente;
    private int id_libro;

    public Pedido(){}

    public Pedido(int id_pedido, int cantidad, int id_cliente, int id_libro) {
        this.id_pedido = id_pedido;
        this.cantidad = cantidad;
        this.id_cliente = id_cliente;
        this.id_libro = id_libro;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }
}
