package com.registro.usuarios.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registro.usuarios.modelo.Usuario;


public interface UsuarioServicio extends UserDetailsService{

	public Usuario guardar(Usuario usuario);
	
	public List<Usuario> listarUsuarios();


	
}
