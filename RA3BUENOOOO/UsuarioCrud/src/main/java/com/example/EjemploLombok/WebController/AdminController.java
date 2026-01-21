package com.example.EjemploLombok.WebController;


import com.example.EjemploLombok.DTO.UsuarioSesionDTO;
import com.example.EjemploLombok.Modelo.Usuario;
import com.example.EjemploLombok.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioService usuarioService;

    @ResponseBody
    @GetMapping("/datosAdmin")
    public ResponseEntity<UsuarioSesionDTO> mostrarDatosAdmin(HttpSession session){

        UsuarioSesionDTO usuarioSesionDTO = (UsuarioSesionDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesionDTO == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            Optional<Usuario> usuarioOptional = usuarioService.buscarUsuario(usuarioSesionDTO.getUsername());
            UsuarioSesionDTO usuarioAmostrar = new UsuarioSesionDTO(usuarioOptional.get().getIdUsuario(), usuarioOptional.get().getUsername(), usuarioOptional.get().getRoles().iterator().next().getNombre(), usuarioOptional.get().getFechaCreacion());

            return ResponseEntity.ok(usuarioAmostrar);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public String vaidarAdmin(){
        return "Admin/ListaUsuariosAdmin";
    }

    @ResponseBody
    @GetMapping("/verUsuarios")
    public ArrayList<UsuarioSesionDTO> mostrarUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<>();
        ArrayList<UsuarioSesionDTO> listaDTO = new ArrayList<>();



        try{
            lista = usuarioService.mostrarUsuariosService();
            for (Usuario usuario : lista){

                listaDTO.add(new UsuarioSesionDTO(usuario.getIdUsuario(), usuario.getUsername(), usuario.getRoles().iterator().next().getNombre(), usuario.getFechaCreacion(), usuario.isActivo()));

            }

            if(lista.isEmpty()) throw new IllegalStateException("No hay usuarios disponibles");
            return listaDTO;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return listaDTO;
        }



    }




}
