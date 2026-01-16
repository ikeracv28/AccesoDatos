package com.example.CrudUsuarioIker.Services;

import ch.qos.logback.core.util.StringUtil;
import com.example.CrudUsuarioIker.Model.Usuario;
import com.example.CrudUsuarioIker.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean verificarEmail(String email){
        if(StringUtils.hasText(email)){
            if(usuarioRepository.findByEmail(email)){
                System.out.println("Ya hay un usuario registrado con ese email");
                return false;

            }else{
                return true;
            }
        }
        return false;
    }



    public Usuario crearUsuario(Usuario usuario){
        if(usuario == null){
            throw new IllegalArgumentException("Usario no valido");
        }
        if (verificarEmail(usuario.getEmail())){
            String passwordHash = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordHash);

            return usuarioRepository.save(usuario);
        }else{
            throw new IllegalStateException("Este usuario ya existe");
        }
    }

    public boolean comprobacionContraseña(String password, String passwordDB) {
        return passwordEncoder.matches(password, passwordDB);
    }

    public boolean eliminarUsuario(int idUsuario){
        return usuarioRepository.deleteUsuarioByIdUsuario(idUsuario);
    }

    public ArrayList<Usuario> mostrarUsuarios(){
        return usuarioRepository.searchAll();
    }

    public Optional<Usuario> mostrarUsuarioId(int idUsuario){
        return usuarioRepository.searchUsuarioByIdUsuario(idUsuario);
    }


    public Usuario editarUsuario(Usuario usuario){
        if(usuario == null) throw new IllegalArgumentException("Usuario nulo");

        Optional<Usuario> existe = usuarioRepository.searchUsuarioByIdUsuario(usuario.getIdUsuario());

        if (existe.isEmpty())
            throw new IllegalStateException("El usuario no existe");


        return usuarioRepository.save(usuario);
    }


}
