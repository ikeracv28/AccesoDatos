package com.example.EjemploLombok.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "Usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idUsuario;

    @Column(name = "username",unique = true, length = 100, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo")
    private boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarioRol",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private Set<Rol> roles = new HashSet<>();


// ========================================
// CALLBACKS DEL CICLO DE VIDA
// ========================================

    /**
     * Se ejecuta automáticamente ANTES de insertar en BBDD.
     * Establece la fecha de creación y actualización.
     */

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Se ejecuta automáticamente ANTES de actualizar en BBDD.
     * Actualiza la fecha de última modificación.
     */
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
