package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private int idTarea;

    @Column(name = "id_empleado")
    private int idEmpleado;

    @Column(name = "id_proyecto")
    private int idProyecto;

    @Column(name = "horas")
    private double horas;

    public Tarea( int idEmpleado, int idProyecto, double horas) {
        this.idEmpleado = idEmpleado;
        this.idProyecto = idProyecto;
        this.horas = horas;
    }

}
