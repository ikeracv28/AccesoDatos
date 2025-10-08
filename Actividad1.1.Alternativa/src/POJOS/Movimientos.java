package POJOS;

public class Movimientos {
    private boolean tipo;
    private double cantidad;
    private String fecha;
    private String concepto;

    public Movimientos(boolean tipo, double cantidad, String concepto) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.concepto = concepto;
    }

    public boolean getTipo() {
        return tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public String getConcepto() {
        return concepto;
    }


}
