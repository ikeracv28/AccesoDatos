package Conexion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionHikari {
    // DataSource compartido por toda la aplicación
    private static HikariDataSource dataSource;


    static {
        try {
            Properties props = new Properties();

            // try-with-resources para cerrar automáticamente el InputStream
            // Se utiliza ConexionBD.class.getClassLoader() para obtener el classloader.
            try (InputStream input = ConexionHikari.class.getClassLoader().getResourceAsStream("db.properties")) {
                // Carga de las propiedades desde el fichero de configuración.
                // Se espera que 'db.url', 'db.user' y 'db.password' estén presentes.
                props.load(input);
            }

            // Configuración mínima de HikariCP. Se explican los parámetros relevantes.
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));           // URL JDBC, p.ej. jdbc:mysql://host:3306/schema
            config.setUsername(props.getProperty("db.user"));         // Usuario de la BD
            config.setPassword(props.getProperty("db.password"));     // Contraseña de la BD

            // Parámetros de tuning básicos (ejemplo didáctico)
            config.setMaximumPoolSize(5);     // Número máximo de conexiones activas en el pool
            config.setMinimumIdle(2);        // Número mínimo de conexiones inactivas a mantener
            config.setIdleTimeout(10000);    // Tiempo (ms) tras el cual una conexión inactiva puede cerrarse
            config.setConnectionTimeout(10000); // Tiempo (ms) máximo a esperar por una conexión libre
            config.setPoolName("DAMPool");   // Nombre del pool para facilitar diagnóstico

            // Creación del DataSource (pool) a partir de la configuración anterior.
            dataSource = new HikariDataSource(config);
            System.out.println("Pool de conexiones HikariCP inicializado correctamente.");
        } catch (Exception e) {

            throw new RuntimeException("Error al inicializar el pool de conexiones", e);
        }
    }


    public static Connection getConexion() throws SQLException {
        return dataSource.getConnection();
    }

    public static void cerrarPool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexiones cerrado.");
        }
    }
}