import NivelBasico.Estudiante;
import NivelBasico.ExportarCSVProfe;
import NivelBasico.ExportarXML;
import NivelBasico.ExportarJSON;

import NivelMedio.Libro;
import NivelMedio.ExportarCSV_Biblioteca;
import NivelMedio.ExportarXML_Biblioteca;
import NivelMedio.ExportarJSON_Biblioteca;

import NivelDificil.Cliente;
import NivelDificil.Habitacion;
import NivelDificil.Reserva;
import NivelDificil.ExportarCSV_Hotel;
import NivelDificil.ExportarXML_Hotel;
import NivelDificil.ExportarJSON_Hotel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        exportarFacil();
        exportarMedio();
        exportarDificil();
        System.out.println("✔ Exportaciones Fácil, Medio y Difícil generadas con timestamp.");
    }

    // =========================
    // NIVEL 1 — FÁCIL (Estudiantes)
    // =========================
    public static void exportarFacil() {
        List<Estudiante> estudiantes = List.of(
                new Estudiante(1, "Juan", "García López", 20, 8.5),
                new Estudiante(2, "María", "Rodríguez", 19, 9.2),
                new Estudiante(3, "Pedro", "Martínez", 21, 7.8),
                new Estudiante(4, "Ana", "López", 20, 8.9),
                new Estudiante(5, "Carlos", "Sánchez", 22, 6.5)
        );

        new ExportarCSVProfe().exportarCSV(estudiantes);
        ExportarXML.escribirXmlExacto(estudiantes);
        ExportarJSON.escribirJsonExacto(estudiantes);
    }

    // =========================
// NIVEL 2 — MEDIO (Biblioteca)
// =========================
    public static void exportarMedio() {
        // Constructor: (prestamos, disponible, numPaginas, anioPublicacion, categoria, autor, titulo, isbn)
        List<Libro> libros = Arrays.asList(
                new Libro(150, true, 863, 1605, "Clásico", "Miguel de Cervantes", "Don Quijote de la Mancha", "978-84-123456-1"),
                new Libro(200, false, 471, 1967, "Ficción", "Gabriel García Márquez", "Cien Años de Soledad", "978-84-123456-2"),
                new Libro(120, true, 328, 1949, "Distopía", "George Orwell", "1984", "978-84-123456-3"),
                new Libro(90, true, 350, -800, "Épica", "Homero", "La Odisea", "978-84-123456-4"),
                new Libro(500, true, 223, 1997, "Fantasía", "J.K. Rowling", "Harry Potter y la Piedra Filosofal", "978-84-123456-5")
        );

        new ExportarCSV_Biblioteca().exportarCSV(libros);
        ExportarXML_Biblioteca.escribirXmlExacto(libros);
        ExportarJSON_Biblioteca.escribirJsonExacto(libros);
    }



    // =========================
    // NIVEL 3 — DIFÍCIL (Hotel)
    // =========================
    public static void exportarDificil() {
        // Clientes
        List<Cliente> clientes = Arrays.asList(
                new Cliente(1, "Juan García", "juan@email.com", "666111222"),
                new Cliente(2, "María López", "maria@email.com", "611223344"),
                new Cliente(3, "Carlos Pérez", "carlos@email.com", "622334455"),
                new Cliente(4, "Ana Sánchez", "ana@email.com", "633445566")
        );

        // Habitaciones
        List<Habitacion> habitaciones = Arrays.asList(
                new Habitacion(101, "Doble", 90.00, false),
                new Habitacion(102, "Doble", 95.00, true),
                new Habitacion(203, "Suite", 200.00, true),
                new Habitacion(204, "Suite", 210.00, false),
                new Habitacion(305, "Individual", 50.00, true)
        );

        // Reservas
        List<Reserva> reservas = Arrays.asList(
                new Reserva(1, clientes.get(0), habitaciones.get(0),
                        LocalDate.of(2025, 10, 20), LocalDate.of(2025, 10, 23), 3, 270.00, "Confirmada"),
                new Reserva(2, clientes.get(1), habitaciones.get(2),
                        LocalDate.of(2025, 10, 21), LocalDate.of(2025, 10, 25), 4, 800.00, "Confirmada"),
                new Reserva(3, clientes.get(2), habitaciones.get(1),
                        LocalDate.of(2025, 10, 22), LocalDate.of(2025, 10, 24), 2, 190.00, "Cancelada"),
                new Reserva(4, clientes.get(3), habitaciones.get(4),
                        LocalDate.of(2025, 10, 23), LocalDate.of(2025, 10, 26), 3, 150.00, "Completada"),
                new Reserva(5, clientes.get(0), habitaciones.get(3),
                        LocalDate.of(2025, 10, 25), LocalDate.of(2025, 10, 27), 2, 400.00, "Confirmada")
        );

        new ExportarCSV_Hotel().exportarCSV(reservas);
        ExportarXML_Hotel.escribirXmlExacto(reservas);
        ExportarJSON_Hotel.escribirJsonExacto(reservas);
    }
}
