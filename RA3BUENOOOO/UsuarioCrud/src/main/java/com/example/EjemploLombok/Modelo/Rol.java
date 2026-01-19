package com.example.EjemploLombok.Modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "rol")
@Builder
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")
    private int idRol;

    @Column(name = "nombre", unique = true, length = 100, nullable = false)
    private String nombre;


    @ManyToMany( mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Usuario> usuario = new HashSet<>();





    }
