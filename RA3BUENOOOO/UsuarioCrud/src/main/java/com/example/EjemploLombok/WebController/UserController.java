package com.example.EjemploLombok.WebController;


import com.example.EjemploLombok.DTO.UsuarioSesionDTO;
import com.example.EjemploLombok.Modelo.Usuario;
import com.example.EjemploLombok.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    UsuarioService usuarioService;




    @ResponseBody
    @PostMapping("/datos")
    public ResponseEntity<Usuario> mostrarDatos(HttpSession session){
        UsuarioSesionDTO usuarioSesionDTO = (UsuarioSesionDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesionDTO != null){
            try{
                usuarioSesionDTO.
            }
        }
    }


}
