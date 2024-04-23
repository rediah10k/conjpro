package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.modelo.user.CustomUserDetails;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String doc) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByDocumento(Long.parseLong(doc));
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }

        return new CustomUserDetails(usuario);
    }
}
