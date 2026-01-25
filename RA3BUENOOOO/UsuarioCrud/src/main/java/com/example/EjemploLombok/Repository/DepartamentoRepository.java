package com.example.EjemploLombok.Repository;

import com.example.EjemploLombok.Modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}
