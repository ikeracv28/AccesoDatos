package com.dam.accesodatos.miprimeraapi.controller;


import com.dam.accesodatos.miprimeraapi.model.Persona;
import com.dam.accesodatos.miprimeraapi.model.Tarea;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @GetMapping
    public List<Tarea> obtenerTareas() {
        return Arrays.asList(
                new Tarea(1, "Sacar al perro", false),
                new Tarea(2, "Ir a comprar", true),
                new Tarea(3, "Estudiar para examen", true)
        );
    }
}
