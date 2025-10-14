import java.io.Serializable;

public class Movimiento implements Serializable {
    double cantidad;
    String tipo;

    public Movimiento(String tipo, double cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "cantidad=" + cantidad +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public Movimiento() {}

    public double getCantidad() {

        return tipo.equals("Ingreso")  ? cantidad : -cantidad ;
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
