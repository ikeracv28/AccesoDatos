package com.example.EjemploLombok.Service;

import com.example.EjemploLombok.Modelo.Departamento;
import com.example.EjemploLombok.Repository.DepartamentoRepository;
import com.example.EjemploLombok.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;



}
