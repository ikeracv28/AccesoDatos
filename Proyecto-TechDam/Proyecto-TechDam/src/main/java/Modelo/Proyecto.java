package Modelo;

import java.math.BigDecimal;

public class Proyecto {

    private int idProyecto;
    private String nombre;
    private BigDecimal presupuesto;



    public Proyecto(int idProyecto, String nombre, BigDecimal presupuesto) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
    }

    public Proyecto() {}

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "idProyecto=" + idProyecto +
                ", nombre='" + nombre + '\'' +
                ", presupuesto=" + presupuesto +
                '}';
    }

}
