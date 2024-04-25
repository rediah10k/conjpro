package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.UsuarioDTO;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UsuarioControlador {

	private final UsuarioServicio servicio;

	@ModelAttribute("usuario")
	public Usuario retornarNuevoUsuarioRegistroDTO() {
		return new Usuario();
	}

	@PostMapping("/registro")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") Usuario registroDTO) {

		servicio.guardar(registroDTO);

 		return "redirect:/registro?exito";
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
}
