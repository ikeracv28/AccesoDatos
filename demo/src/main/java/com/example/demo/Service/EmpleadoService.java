package com.example.demo.Service;

import com.example.demo.Models.Empleado;
import com.example.demo.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoService {
    // Esto te crea un objeto de la clase que pongamos debajo
    @Autowired
    EmpleadoRepository empleadRepository;

    public Optional<Empleado> buscarEmpleadoPorId(int id){

        Optional<Empleado> empleado = empleadRepository.getEmpleadoById(id);

        return empleado;
    }

}
