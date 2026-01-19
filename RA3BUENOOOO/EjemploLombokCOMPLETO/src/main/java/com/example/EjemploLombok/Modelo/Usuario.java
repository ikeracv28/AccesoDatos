package com.example.EjemploLombok.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "Usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username",unique = true, length = 100, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo")
    private boolean activo = true;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "contador_intentos", nullable = false)
    private int contadorIntentos = 0;

    @Column(name = "estado", nullable = false)
    private boolean estado = true;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario(String username) {
        this.username = username;
    }
}
