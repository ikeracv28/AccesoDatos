public class Ventas {
    private String DNI;
    private String ISBN;
    private String fecha;
    private int unidades;
    private double precioTotalVentas;

    public Ventas(){}

    public Ventas(String DNI, String ISBN, double precioTotalVentas, String fecha, int unidades) {
        this.DNI = DNI;
        this.ISBN = ISBN;
        this.precioTotalVentas = precioTotalVentas;
        this.fecha = fecha;
        this.unidades = unidades;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecioTotalVentas() {
        return precioTotalVentas;
    }

    public void setPrecioTotalVentas(double precioTotalVentas) {
        this.precioTotalVentas = precioTotalVentas;
    }


}


