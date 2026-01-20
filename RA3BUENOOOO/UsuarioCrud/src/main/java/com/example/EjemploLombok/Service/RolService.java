package com.example.EjemploLombok.Service;

import com.example.EjemploLombok.Modelo.Rol;
import com.example.EjemploLombok.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public List<Rol> obtenerRoles(){
        return rolRepository.findAll();
    }

}
