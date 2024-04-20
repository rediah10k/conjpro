package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
		model.addAttribute("listarPropietarios",servicio.listarNoExternos());
		model.addAttribute("listarUsuarios",servicio.listarUsuarios());
		return "registro";
	}

	@PostMapping("/registro")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") Usuario registroDTO) {
		Usuario delegado;
		Usuario delegante;
		delegante = repoUser.findByIdUsuario(registroDTO.getIdUsuario());
		if(registroDTO.getDocumento()==null){
			delegado=repoUser.findByIdUsuario(registroDTO.getDelegado().getIdUsuario());
		}else{
			registroDTO.setIdUsuario(null);
			servicio.guardar(registroDTO);
			delegado = repoUser.findByDocumento(registroDTO.getDocumento());
		}
		delegante.setDelegado(delegado);
		servicio.actualizarApoderado(delegante);

 		return "redirect:/registro?exito";
	}







}
