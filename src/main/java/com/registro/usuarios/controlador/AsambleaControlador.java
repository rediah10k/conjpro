package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.servicio.AsambleaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class AsambleaControlador {

    @Autowired
    private AsambleaServicio asambleaS;


    @PostMapping("/crearAsamblea")
    public ResponseEntity<String> crearAsamblea(@RequestBody Asamblea asamblea) {
        return ResponseEntity.ok(asambleaS.crearAsamblea(asamblea));
    }


    @PostMapping("/ingresarAsamblea")
    public String ingresarAsamblea(@RequestParam("code") String code, Model model) {
        return asambleaS.ingresarAsamblea(code, model);
    }

}
