package com.example.EjemploLombok.Service;

import ch.qos.logback.core.util.StringUtil;
import com.example.EjemploLombok.Modelo.Usuario;
import com.example.EjemploLombok.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    PasswordEncoder passwordEncoder; // Necesario para hashear la contraseña

    public Usuario crearUsuario(Usuario usuario){
        if (usuario == null) throw new IllegalArgumentException("El usuario viene vacio al service");

        if (usuarioRepository.existsByUsername(usuario.getUsername())) throw new IllegalStateException("El usuario ya existe");

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);

    }

    public boolean comprobarUsername(String username){
        if(!StringUtils.hasText(username))throw new IllegalArgumentException("El username no puede estar vacio");

        return usuarioRepository.existsByUsername(username);
    }

    public boolean comprobarInicioSesion(Usuario usuarioraw){
        if (usuarioraw == null) throw new IllegalArgumentException("El usuario viene vacio al service");
        Optional <Usuario> usuariobd = usuarioRepository.findUsuarioByUsername(usuarioraw.getUsername());
        if(usuariobd.isEmpty()) throw new IllegalStateException("El usuario no existe");


        //Verifica que la contraseña que mete el usuario compara una contraseña sin hashear con una hasheada.
        if(!passwordEncoder.matches(usuarioraw.getPassword(),usuariobd.get().getPassword())){
            int numeroFallos = usuariobd.get().getContadorIntentos()+1;
            usuariobd.get().setContadorIntentos(numeroFallos);
            if (usuariobd.get().getContadorIntentos()==3){
                usuariobd.get().setEstado(false);
            }
            usuarioRepository.save(usuariobd.get());
            return false;
        }

        return true;
    }

    public ArrayList<Usuario> mostrarUsuariosService(){
        return usuarioRepository.findAllBy();
    }

    public Optional<Usuario> buscarUsuario(String username){
        if (username == null) throw new IllegalArgumentException("El nombre introducido no existe");
        if (!comprobarUsername(username)) throw new IllegalStateException("El usuario no existe");
        Optional<Usuario> usuarioBuscado = usuarioRepository.findUsuarioByUsername(username);
        if(usuarioBuscado.isEmpty()) throw new IllegalStateException("El usuario es nulo");
        return usuarioBuscado;
    }

    @Transactional
    public Usuario updateUsuario(Usuario usuario){
        if (usuario == null) throw new IllegalStateException("El usuario actualizado esta vacio");
        Optional <Usuario> usuariodb = usuarioRepository.findById(usuario.getId());
        if (usuariodb.isEmpty()) throw new IllegalArgumentException("El usuario está vacio");
        if (!usuario.getPassword().equals(usuariodb.get().getPassword())){
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

       return usuarioRepository.save(usuario);

    }

    @Transactional
    public Usuario updateEstado(Usuario usuario){
        if (usuario == null) throw new IllegalStateException("El usuario actualizado esta vacio");

        return usuarioRepository.save(usuario);

    }

    @Transactional
    public void deleteUsuario(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public void ultimoLogin(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public boolean validarContrasena(String username, String password){
        Optional<Usuario> usuario = usuarioRepository.findUsuarioByUsername(username);
        if (passwordEncoder.matches(password,usuario.get().getPassword())){
            return true;
        }else {
            return false;
        }
    }

    public boolean cambiarContrasena(String username, String password){
        Optional<Usuario> usuario = usuarioRepository.findUsuarioByUsername(username);
        String contrasenaVieja = usuario.get().getPassword();
        usuario.get().setPassword(passwordEncoder.encode(password));

        usuarioRepository.save(usuario.get());
        if (contrasenaVieja.equals(usuario.get().getPassword())){
            return false;
        }else {
            return true;
        }
    }

    public Page<Usuario> listarUsuariosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }


}
