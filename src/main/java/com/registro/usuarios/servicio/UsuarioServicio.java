package com.registro.usuarios.servicio;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.registro.usuarios.modelo.Usuario;


public interface UsuarioServicio extends UserDetailsService{

	public Usuario guardar(Usuario registroDTO);
	
	public List<Usuario> listarUsuarios();
	
}
