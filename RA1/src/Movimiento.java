import java.io.Serializable;

public class Movimiento implements Serializable {
    double cantidad;
    String tipo;

    public Movimiento(String tipo, double cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // es para ver los datos de Movimiento de form legible, me lo genera el propio intellij
    @Override
    public String toString() {
        return "Movimiento" +
                "cantidad=" + cantidad +
                ", tipo='" + tipo + '\'';
    }

    public Movimiento() {}

    public double getCantidad() {

        // esto hce que ql sumar los moviminetos, si es ingreso se suman y si es retirada se resten, es un especie de if corto
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
