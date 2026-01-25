package com.example.EjemploLombok.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UsuarioSesionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idUsuario;
    private String username;
    private String nombreRol;
    private LocalDateTime fechaCreacion;
    private boolean estado;
    private String nombreDepartamento;

    public UsuarioSesionDTO(int idUsuario, String username, String nombreRol, LocalDateTime fechaCreacion, String nombreDepartamento) {
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.nombreRol = nombreRol;
        this.username = username;
        this.nombreDepartamento = nombreDepartamento;
    }
}
