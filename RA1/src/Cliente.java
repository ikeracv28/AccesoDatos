import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {
    private String nombre;
    private String DNI;
    private String nCuenta;


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
