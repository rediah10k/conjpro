package com.proisii.conj.controller;

import com.proisii.conj.entity.Usuario;
import com.proisii.conj.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> todosLosUsuarios(){
        return usuarioService.obtenerTodosLosUsuarios();
    }

}


