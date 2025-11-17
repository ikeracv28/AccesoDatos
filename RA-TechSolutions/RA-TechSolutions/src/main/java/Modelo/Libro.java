package Modelo;

public class Libro {
    private int id_libro;
    private String titulo;
    private String autor;
    private double precio;
    private int stock;
    private int id_genero;

    public Libro(){}

    public Libro(int id_libro, String titulo, String autor, double precio, int stock, int id_genero) {
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
        this.id_genero = id_genero;
    }

    public int getId_libro() {
        return id_libro;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}