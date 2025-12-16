package com.example.demo.Repository;

import com.example.demo.Models.Empleado;
import com.example.demo.Models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findTareasByIdEmpleado(int idEmpleado);


}
