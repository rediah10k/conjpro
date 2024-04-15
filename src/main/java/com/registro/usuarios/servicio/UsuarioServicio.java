package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.RolRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements UserDetailsService {

	
	private UsuarioRepositorio usuarioRepositorio;
	private RolRepositorio rolRepositorio;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	
	public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.rolRepositorio=rolRepositorio;
	}


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
		//registroDTO.setExterno(true);
				Usuario usuario = new Usuario(registroDTO.getNombre(),
				registroDTO.getApellido(),registroDTO.getDocumento(),
				passwordEncoder.encode(registroDTO.getDocumento().toString()),registroDTO.getCorreo(),rol);

		return usuarioRepositorio.save(usuario);
	}
	@Transactional
	public String obtenerRol(String documento){
		Usuario usuario = usuarioRepositorio.findByDocumento(Long .parseLong(documento));
		if(usuario.getRol().getIdRol()==1){
			return "admin";
		}else{
			return "usuario";
		}

	}

	@Override
	public UserDetails loadUserByUsername(String doc) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByDocumento(Long.parseLong(doc));
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(usuario.getDocumento().toString(),usuario.getContrasena(), mapearAutoridadesRoles(usuario.getRol()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Rol rol){
		return Collections.singleton(new SimpleGrantedAuthority(rol.getNombre()));
	}

	private Set<GrantedAuthority> getAuthorities(Set<Rol> roles){
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLES_"+role.getNombre()))
				.collect(Collectors.toSet());
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepositorio.findAll();
	}
}
