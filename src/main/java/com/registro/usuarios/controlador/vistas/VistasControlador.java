package com.registro.usuarios.controlador.vistas;

import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VistasControlador {

    private final UsuarioServicio servicio;
    private final UsuarioRepositorio repoUser;

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/index")
    public String verPaginaInicioUsuario(Model modelo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        modelo.addAttribute("sesionUser", repoUser.findByDocumento(Long.parseLong(authentication.getName())));

        return "index";
    }

    @GetMapping("/indexAdmin")
    public String verPaginaInicioAdmin(Model modelo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        modelo.addAttribute("sesionUser", repoUser.findByDocumento(Long.parseLong(authentication.getName())));
        return "indexAdmin";
    }

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        // Agrega un punto de control para verificar si el objeto "usuario" está presente en el modelo
        Usuario usuario = new Usuario(); // O obtén el usuario de alguna otra fuente
        model.addAttribute("usuario", usuario);

        // Agrega otros atributos al modelo si es necesario
        model.addAttribute("listarPropietarios", servicio.listarNoExternos());
        model.addAttribute("listarUsuarios", servicio.listarUsuarios());

        return "registro";
    }

    @GetMapping("/crearAsamblea")
    public String crearAsamblea(Model model) {

        Asamblea asamblea = new Asamblea();
        asamblea.setVotoCoeficiente(false);
        model.addAttribute("asamblea", asamblea);

        return "crearAsamblea";
    }

    @GetMapping("/ingresarAsamblea")
    public String ingresarAsamblea(Model model) {
        return "ingresarAsamblea";
    }
}
