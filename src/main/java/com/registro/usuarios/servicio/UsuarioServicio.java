package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.UsuarioDTO;
import com.registro.usuarios.modelo.*;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.repositorio.ConjuntoRepositorio;
import com.registro.usuarios.repositorio.RolRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UsuarioServicio{

	private final UsuarioRepositorio usuarioRepositorio;
	private final RolRepositorio rolRepositorio;
	private final ConjuntoRepositorio conjuntoRepositorio;
	private final AsambleaRepositorio asambleaRepositorio;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void actualizarApoderado(Usuario usuario){
		usuarioRepositorio.save(usuario);
	}

	public Usuario guardarUsuario(UsuarioDTO usuarioDTO){
		Usuario usuario = new Usuario();
		Rol rol = new Rol(TipoRol.USUARIO,2L);
		Conjunto conjunto = conjuntoRepositorio.findById(Long.parseLong(usuarioDTO.getConjunto())).orElse(null);

		if (conjunto == null)
			throw new RuntimeException();

		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellido(usuarioDTO.getApellido());
		usuario.setDocumento(Long.parseLong(usuarioDTO.getDocumento()));
		usuario.setExterno(false);
		usuario.setRoles(Set.of(rol));
		usuario.setCorreo(usuarioDTO.getCorreo());
		usuario.setConjunto(conjunto);
		usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getDocumento()));

		return usuarioRepositorio.save(usuario);
	}

	public Usuario guardarUsuarioExterno(Usuario registroDTO) {
		Rol rol = rolRepositorio.findByNombre(TipoRol.USUARIO);

		Usuario usuarioExistente = usuarioRepositorio.findByDocumento(registroDTO.getDocumento());

		if (usuarioExistente!=null)
			throw new RuntimeException("El usuario ya existe");

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
		usuario.setConjunto(registroDTO.getConjunto());
		usuarioRepositorio.save(usuario);
		return usuario;

	}

	public String guardar(Usuario registroDTO, String codigoAsamblea, Model model){
		Usuario delegado;
		Usuario delegante;

		Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigoAsamblea);

		List<Usuario> usuariosDelegados;

		delegante = usuarioRepositorio.findByIdUsuario(registroDTO.getIdUsuario());
		if(registroDTO.getDocumento()==0){
			delegado=usuarioRepositorio.findByIdUsuario(registroDTO.getDelegado().getIdUsuario());

		}else{
			registroDTO.setIdUsuario(0);
			//registroDTO.setConjunto(delegante.getConjunto());
			guardarUsuarioExterno(registroDTO);
			delegado = usuarioRepositorio.findByDocumento(registroDTO.getDocumento());

		}

		usuariosDelegados = usuarioRepositorio.findAllByDelegado(delegado);

		if (usuariosDelegados.size()<Integer.parseInt(asamblea.getPoderesMax())){
			delegante.setDelegado(delegado);
			actualizarApoderado(delegante);

			return "redirect:/registro";
		}
		else{
			model.addAttribute("error", "El delegado ya tiene el número máximo de delegantes");
		}

		return "registro";
	}

	public List<UsuarioDTO> encontrarUsuariosPorConjunto(String idConjunto){
		Conjunto conjunto = conjuntoRepositorio.findById(Long.valueOf(idConjunto)).orElse(null);

		if (conjunto == null)
			return null;

		List<Usuario> usuarios = usuarioRepositorio.findAllByConjunto(conjunto);

		return usuarios.stream().map(usuario -> {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setConjunto(usuario.getConjunto().getNombre());
			usuarioDTO.setNombre(usuario.getNombre());
			usuarioDTO.setDocumento(String.valueOf(usuario.getDocumento()));
			usuarioDTO.setCorreo(usuario.getCorreo());
			usuarioDTO.setApellido(usuario.getApellido());

			return usuarioDTO;
		}).toList();
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

	public List<Usuario> consultarSiEsDelegado(String idUsuario){

		Usuario delegado = usuarioRepositorio.findByIdUsuario(Long.parseLong(idUsuario));

		return usuarioRepositorio.findAllByDelegado(delegado);
	}
}
