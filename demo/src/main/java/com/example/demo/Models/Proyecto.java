package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @Column(name = "id_proyecto")
    private int id_proyecto;


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;


}
