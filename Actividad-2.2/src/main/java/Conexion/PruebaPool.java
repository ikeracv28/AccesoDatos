package Conexion;

import Conexion.ConexionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Ejemplo sencillo que demuestra cómo obtener una conexión desde un pool
 * (HikariCP, representado por la clase ConexionDB_HikariCP) y cómo liberar
 * los recursos del pool al finalizar la aplicación.
 *
 * Propósito académico:
 * - Mostrar el uso de try-with-resources para gestionar la conexión.
 * - Ilustrar el manejo básico de SQLException.
 * - Explicar el cierre explícito del pool al terminar el programa.
 *
 * No se modifica la lógica original; sólo se añaden comentarios explicativos.
 */
public class PruebaPool {

    /**
     * Punto de entrada de la aplicación de ejemplo.
     *
     * Flujo:
     * 1. Se solicita una conexión al pool mediante ConexionDB_HikariCP.getConexion().
     * 2. Si la obtención es correcta, se muestra un mensaje de confirmación.
     * 3. Cualquier SQLException producida se captura e imprime la traza.
     * 4. En el bloque finally se invoca ConexionDB_HikariCP.cerrarPool() para
     *    liberar los recursos del pool antes de finalizar la JVM.
     */
    public void conexiones() {
        /*
         * try-with-resources asegura que la referencia Connection 'con' se cierre
         * automáticamente cuando se salga del bloque try, incluso si se lanza una excepción.
         * En este ejemplo la conexión proviene de un pool (HikariCP), por lo que cerrar
         * la Connection devuelve la instancia al pool en lugar de cerrarla físicamente.
         */
        try (Connection con1 = ConexionPool.getConexion();
             Connection con2 = ConexionPool.getConexion();
            Connection con3 = ConexionPool.getConexion()) {
            if(con1 != null){
                // Confirmación sencilla de que la conexión fue obtenida correctamente.

                Statement st = con1.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== EMPLEADOS ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                }
                System.out.println("Conexión1 obtenida del pool correctamente.");
            }
            if(con2 != null){
                // Confirmación sencilla de que la conexión fue obtenida correctamente.

                Statement st = con2.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== EMPLEADOS ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));

                }
                System.out.println("Conexión2 obtenida del pool correctamente.");
            }
            if(con3 != null){
                // Confirmación sencilla de que la conexión fue obtenida correctamente.

                Statement st = con3.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM empleados");

                System.out.println("\n=== EMPLEADOS ===");
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                }
                System.out.println("Conexión3 obtenida del pool correctamente.");
            }


        } catch (SQLException e) {
            /*
             * Captura específica de SQLException: en un contexto real se podría
             * registrar el error en un logger y tomar decisiones según el código SQLState.
             * Aquí, por simplicidad didáctica, se imprime la traza de la excepción.
             */
            e.printStackTrace();

        } finally {
            /*
             * El pool de conexiones (DataSource gestionado por HikariCP) debe cerrarse
             * explícitamente cuando la aplicación ya no lo necesita. Llamar a
             * ConexionDB_HikariCP.cerrarPool() garantiza la liberación ordenada de
             * recursos del pool (hilos, conexiones físicas, etc.).
             *
             * Nota académica: en aplicaciones long-running (servidores) el cierre del
             * pool suele gestionarse en el shutdown hook o en el lifecycle del contenedor,
             * no inmediatamente después de cada operación.
             */
            ConexionPool.cerrarPool();
        }
    }
}