package com.example.EjemploLombok.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UsuarioSesionDTO {
    private int idUsuario;
    private String username;
    private String nombreRol;

}
