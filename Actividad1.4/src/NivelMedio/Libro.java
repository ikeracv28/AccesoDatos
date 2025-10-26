package NivelMedio;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String categoria;
    private int anioPublicacion;
    private int numPaginas;
    private boolean disponible;
    private int prestamos;

    public Libro(int prestamos, boolean disponible, int numPaginas, int anioPublicacion, String categoria, String autor, String titulo, String isbn) {
        this.prestamos = prestamos;
        this.disponible = disponible;
        this.numPaginas = numPaginas;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.autor = autor;
        this.titulo = titulo;
        this.isbn = isbn;
    }

    public Libro() {}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(int prestamos) {
        this.prestamos = prestamos;
    }
}