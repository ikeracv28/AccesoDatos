package Modelo;

import java.time.LocalDate;

public class Asignaciones {
    private int id;
    private int id_empleado;
    private int id_proyecto;
    private LocalDate fecha;

    public Asignaciones(int id, int id_empleado, int id_proyecto, LocalDate fecha) {
        this.id = id;
        this.id_empleado = id_empleado;
        this.id_proyecto = id_proyecto;
        this.fecha = fecha;
    }

    public Asignaciones() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
