package com.registro.usuarios.servicio;

import java.util.*;
import java.util.stream.Collectors;

import com.registro.usuarios.repositorio.RolRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private RolRepositorio rolRepositorio;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.rolRepositorio=rolRepositorio;
	}

	@Override
	@Transactional
	public Usuario guardar(Usuario registroDTO) {
		Rol rolOpt = rolRepositorio.findByNombre("usuario");
		Rol rol;
		if (rolOpt==null) {
			rol = new Rol(null, "usuario");
			rol = rolRepositorio.save(rol);
		}else{
			rol=rolOpt;
		}

		Usuario usuario = new Usuario(registroDTO.getNombre(), 
				registroDTO.getApellido(),registroDTO.getDocumento(),
				passwordEncoder.encode(registroDTO.getContrasena()),rol);
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByCorreo(email);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(usuario.getCorreo(),usuario.getContrasena(), mapearAutoridadesRoles(usuario.getRol()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Rol rol){
		return Collections.singleton(new SimpleGrantedAuthority(rol.getNombre()));
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepositorio.findAll();
	}
}
