package POJOS;

import java.io.Serializable;

public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    double cantidad;
    String tipo;
    String concepto;

    public Movimiento(String tipo, double cantidad, String concepto) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.concepto = concepto;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "cantidad=" + cantidad +
                ", tipo='" + tipo + '\'' +
                ", concepto='" + concepto + '\'' +
                '}';
    }

    public Movimiento() {}

    public double getCantidad() {

        // esto hce que ql sumar los moviminetos, si es ingreso se suman y si es retirada se resten, es un especie de if corto
        return tipo.equals("Ingreso")  ? cantidad : -cantidad ;
    }


    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /* public double getCantidad() {
            return cantidad;
        }
    */
    public String getTipo() {
        return tipo;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
