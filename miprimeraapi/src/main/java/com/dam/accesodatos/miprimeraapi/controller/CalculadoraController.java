package com.dam.accesodatos.miprimeraapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraController {
   @GetMapping("/sumar/{num1}/{num2}")
    public String sumar(@PathVariable int num1,@PathVariable int num2){
       return "La suma es " + (num1 + num2) ;
    }

    @GetMapping("/restar/{num1}/{num2}")
    public String restar(@PathVariable int num1,@PathVariable int num2){
        return "La resta es " + (num1 - num2) ;

    }

    @GetMapping("/multiplicar/{num1}/{num2}")
    public String multiplicar(@PathVariable int num1,@PathVariable int num2){
        return "La multiplicacion es " + (num1 * num2) ;

    }


}
