package com.example.CrudUsuarioIker.Repositories;

import com.example.CrudUsuarioIker.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean findByEmail(String email);

    boolean deleteUsuarioByIdUsuario(int idUsuario);

    @Query(value = "select * from usuario", nativeQuery = true)
    ArrayList<Usuario> searchAll();

    Optional<Usuario> searchUsuarioByIdUsuario(int idUsuario);
}
