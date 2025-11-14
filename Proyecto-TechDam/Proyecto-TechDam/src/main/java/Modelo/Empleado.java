package Modelo;

import java.math.BigDecimal;

public class Empleado {

    private int id_empleado;
    private String nombre;
    private String departamento;
    private BigDecimal salario;
    private boolean activo;

    public Empleado(int id_empleado, String nombre, String departamento,  BigDecimal salario, boolean activo)  {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.salario = salario;
        this.departamento = departamento;
        this.activo = activo;
    }

    public Empleado()  {}


    public String getNombre() {
        return nombre;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id_empleado=" + id_empleado +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", salario=" + salario +
                ", activo=" + activo +
                '}';
    }
}
