package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.TipoRol;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.modelo.user.CustomUserDetails;
import com.registro.usuarios.repositorio.RolRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServicio{

	private final UsuarioRepositorio usuarioRepositorio;
	private final RolRepositorio rolRepositorio;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void actualizarApoderado(Usuario usuario){
		usuarioRepositorio.save(usuario);
	}

	public Usuario guardarUsuario(Usuario registroDTO) {
		Rol rol = rolRepositorio.findByNombre(TipoRol.USUARIO);

		Set<Rol> roles = Set.of(rol);

		registroDTO.setExterno(true);
		Usuario usuario = new Usuario();

		usuario.setNombre(registroDTO.getNombre());
		usuario.setApellido(registroDTO.getApellido());
		usuario.setDocumento(registroDTO.getDocumento());
		usuario.setContrasena(passwordEncoder.encode(String.valueOf(registroDTO.getDocumento())));
		usuario.setCorreo(registroDTO.getCorreo());
		usuario.setExterno(registroDTO.isExterno());
		usuario.setRoles(roles);

		usuarioRepositorio.save(usuario);
		return usuario;

	}

	public void guardar(Usuario registroDTO){
		Usuario delegado;
		Usuario delegante;
		delegante = usuarioRepositorio.findByIdUsuario(registroDTO.getIdUsuario());
		if(registroDTO.getDocumento()==0){
			delegado=usuarioRepositorio.findByIdUsuario(registroDTO.getDelegado().getIdUsuario());
		}else{
			registroDTO.setIdUsuario(0);
			guardarUsuario(registroDTO);
			delegado = usuarioRepositorio.findByDocumento(registroDTO.getDocumento());
		}
		delegante.setDelegado(delegado);
		actualizarApoderado(delegante);
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepositorio.findAll();
	}

	public List<Usuario> listarExternos(){
		return usuarioRepositorio.findAllByExterno(true);
	}
	public List<Usuario> listarNoExternos(){
		return usuarioRepositorio.findAllByExterno(false);
	}
}
