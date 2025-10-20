import java.util.ArrayList;

public class Main {
    static ArrayList<Producto> inventario = new ArrayList<>();

    public static void main(String[] args) {
        ejercicio1();
        ejercicio2();
        ejercicio3();
    }

    public static void ejercicio1() {
        inventario.add(new Producto("P001", "Portatil", 899.99));
        inventario.add(new Producto("P002", "Ratón", 25.5));
        inventario.add(new Producto("P003", "Teclado", 45));
        inventario.add(new Producto("P004", "Monitor", 199.99));
        inventario.add(new Producto("P005", "Webcam", 59.90));
        for (Producto producto : inventario) {
            System.out.println(producto);
        }
    }

    public static void ejercicio2() {
        System.out.println("--------------------------------------");
        String codigo = "P003";
        String nombre = "Ratón";
        for (Producto producto : inventario) {
            if (codigo.equalsIgnoreCase(producto.getCodigo())) {
                System.out.println(producto);
            }
            if (nombre.equalsIgnoreCase(producto.getNombre())) {
                System.out.println("si existe ese nombre");
            }
        }
    }

    public static void ejercicio3() {
        System.out.println("--------------------------------------");

        for (Producto producto : inventario) {
            if (producto.getNombre().equalsIgnoreCase("monitor")) {
                producto.setPrecio(179.99);
                System.out.println("precio actualizado: " + producto);
            }
        }
        inventario.removeIf(producto -> producto.getNombre().equalsIgnoreCase("webcam"));

        //inventario.remove("Webcam");
        inventario.add(new Producto("P006", "Altavoces", 35.00));
        System.out.println("Inventario final:");
        for (Producto producto : inventario) {
            System.out.println(producto);

        }
    }

    static void ejercicio4(){



    }


}



