package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.UsuarioDTO;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsuarioControlador {

	private final UsuarioServicio servicio;
	private final UsuarioServicio usuarioServicio;

	@ModelAttribute("usuario")
	public Usuario retornarNuevoUsuarioRegistroDTO() {
		return new Usuario();
	}

	@PostMapping("/registro")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") Usuario registroDTO,@RequestParam String codigoAsamblea,Model model) {

		return servicio.guardar(registroDTO,codigoAsamblea,model);
	}

	@PostMapping("/crearUsuario")
	public ResponseEntity<?> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO){

		try{
			Usuario usuario = servicio.guardarUsuario(usuarioDTO);
			return ResponseEntity.ok(usuario);
		}
		catch (Exception e){
			return ResponseEntity.badRequest().body("Hubo un problema en el servidor");
		}
	}

	@GetMapping("usuarios_conjunto")
	public ResponseEntity<?> buscarUsuariosPorConjunto(@RequestParam String idConjunto){

		List<UsuarioDTO> usuarios = usuarioServicio.encontrarUsuariosPorConjunto(idConjunto);

		if (usuarios == null)
			return ResponseEntity.of(Optional.of("El conjunto no se encuentra"));

		return ResponseEntity.ok(usuarios);
	}
}
