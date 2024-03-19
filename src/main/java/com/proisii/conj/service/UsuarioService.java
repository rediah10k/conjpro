package com.proisii.conj.service;

import com.proisii.conj.entity.Usuario;
import com.proisii.conj.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioRepository.findAll();
    }

}
