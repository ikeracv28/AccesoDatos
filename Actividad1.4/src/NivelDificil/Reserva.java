package NivelDificil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Representa una reserva. Incluye campos calculados
 * (noches y precioTotal), pero puedes recalcularlos al exportar.
 */
public class Reserva {
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaEntrada; // inclusive
    private LocalDate fechaSalida;  // exclusive
    private int noches;             // puede recalcularse desde las fechas
    private double precioTotal;     // puede recalcularse = noches * precioPorNoche
    private String estado;          // Confirmada, Cancelada, Completada

    public Reserva() { }

    public Reserva(int id,
                   Cliente cliente,
                   Habitacion habitacion,
                   LocalDate fechaEntrada,
                   LocalDate fechaSalida,
                   int noches,
                   double precioTotal,
                   String estado) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.noches = noches;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public int getNoches() { return noches; }
    public void setNoches(int noches) { this.noches = noches; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // --- Utilidades opcionales ---

    /** Calcula noches a partir de las fechas (si ambas no son null). */
    public int calcularNochesPorFechas() {
        if (fechaEntrada != null && fechaSalida != null) {
            long d = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
            return (int) Math.max(0, d);
        }
        return noches;
    }

    /** Calcula precio total = noches * precioPorNoche (si hay habitación). */
    public double calcularPrecioTotal() {
        int n = calcularNochesPorFechas();
        double p = (habitacion == null) ? 0.0 : habitacion.getPrecioPorNoche();
        return n * p;
    }
}
