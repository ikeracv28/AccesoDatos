package NivelDificil;

/**
 * Representa una habitación del hotel.
 * tipo: "Individual", "Doble" o "Suite"
 */
public class Habitacion {
    private int numero;
    private String tipo;           // Individual, Doble, Suite
    private double precioPorNoche; // €/noche
    private boolean disponible;

    public Habitacion() { }

    public Habitacion(int numero, String tipo, double precioPorNoche, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = disponible;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecioPorNoche() { return precioPorNoche; }
    public void setPrecioPorNoche(double precioPorNoche) { this.precioPorNoche = precioPorNoche; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
