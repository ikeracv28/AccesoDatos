package ClasesRA1;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String DNI;
    private String nCuenta;

    public String getnCuenta() {
        return nCuenta;
    }

    public void setnCuenta(String nCuenta) {
        this.nCuenta = nCuenta;
    }

    public Cliente(String nombre, String DNI, String nCuenta) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.nCuenta = nCuenta;
    }
    public Cliente(){}

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
