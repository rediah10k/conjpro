package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio servicio;
	@Autowired
	private UsuarioRepositorio repoUser;

	@GetMapping("/login")
	public String iniciarSesion() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


		if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {

			return "redirect:/";
		}
		return "login";
	}

	@GetMapping("/")
	public String verPaginaInicio(Model modelo){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		modelo.addAttribute("usuarios", servicio.listarUsuarios());
		modelo.addAttribute("sesionUser", repoUser.findByDocumento(Long.parseLong(authentication.getName())));
		return "main";
	}

	@ModelAttribute("usuario")
	public Usuario retornarNuevoUsuarioRegistroDTO() {
		return new Usuario();
	}

	@GetMapping("/registro")
	public String mostrarFormularioDeRegistro(Model model) {
		model.addAttribute("listarUsuarios",servicio.listarUsuarios());
		model.addAttribute("listarExternos",servicio.listarExternos());
		return "registro";
	}

	@PostMapping("/registro")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") Usuario registroDTO) {
		Usuario delegante = registroDTO.getIdApoderado();
		registroDTO.setIdApoderado(null);
		servicio.guardar(registroDTO);
		servicio.actualizarApoderado(delegante,registroDTO.getDocumento());

 		return "redirect:/registro?exito";
	}







}
