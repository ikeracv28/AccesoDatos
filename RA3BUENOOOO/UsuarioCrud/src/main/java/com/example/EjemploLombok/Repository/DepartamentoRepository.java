package com.example.EjemploLombok.Repository;

import com.example.EjemploLombok.Modelo.Departamento;
import com.example.EjemploLombok.Modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {


    Optional<Departamento> findByNombreDepartamento(String nombreDepartamento);
}
