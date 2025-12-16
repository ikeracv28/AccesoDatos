package com.example.demo.Service;

import com.example.demo.Models.Empleado;
import com.example.demo.Models.Tarea;
import com.example.demo.Repository.EmpleadoRepository;
import com.example.demo.Repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    TareaRepository tareaRepository;

    public boolean insertarTareas(Tarea tarea){
        Tarea insertado = tareaRepository.save(tarea);
        if (insertado != null){
            return true;
        } else {
            return false;
        }
    }

    public double horasPorEmpleado(int id){
        List<Tarea> listaTareas = tareaRepository.findTareasByIdEmpleado(id);
        double horas = 0;
        if (listaTareas.isEmpty()){
            System.out.println("No hay empleados con ese id");
        }else {
            for (Tarea tarea : listaTareas){
                horas += tarea.getHoras();
            }
        }
        return horas;
    }







}
