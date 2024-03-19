package com.proisii.conj.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proisii.conj.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean authenticate(Integer username, String password) {
        return usuarioRepository.findByUsuario(username)
                .map(user -> new BCryptPasswordEncoder().matches(password, user.getContrasena()))
                .orElse(false);
    }
}
