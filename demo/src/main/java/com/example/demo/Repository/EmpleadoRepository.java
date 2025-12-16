package com.example.demo.Repository;

import com.example.demo.Models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // esto es para especificarle la consulta que queremos hacer, y nativeQuery es porque es lenguaje sql puro
    @Query(value = "select * from empleado", nativeQuery = true)
    List<Empleado> getAll();

    @Query(value = "SELECT * from empleado where id_empleado = ?1", nativeQuery = true)
    Optional<Empleado> getEmpleadoById(int id);


}
