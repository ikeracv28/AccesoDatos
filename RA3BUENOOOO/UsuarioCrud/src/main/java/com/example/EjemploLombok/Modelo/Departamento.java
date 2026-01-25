package com.example.EjemploLombok.Modelo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDepartamento")
    private int idDepartamento;

    @Column(name = "nombre_departamento", length = 100)
    private String nombreDepartamento;

    @ToString.Exclude
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)

    private List<Usuario> usuarios = new ArrayList<>();

}
