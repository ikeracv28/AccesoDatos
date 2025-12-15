package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id_tarea")
    private int id_tarea;

    @Column(name = "id_empleado")
    private int id_empleado;

    @Column(name = "id_proyecto")
    private int id_proyecto;

    @Column(name = "horas")
    private int horas;


}
