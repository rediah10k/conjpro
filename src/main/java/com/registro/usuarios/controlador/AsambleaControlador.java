package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.servicio.AsambleaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;


@RestController
public class AsambleaControlador {

    @Autowired
    private AsambleaServicio asambleaS;

    @PostMapping("/crearAsamblea")
    public ResponseEntity<String> crearAsamblea(@RequestBody AsambleaDTO asamblea) {
        return ResponseEntity.ok(asambleaS.crearAsamblea(asamblea));
    }

    @GetMapping("/asamblea")
    public ResponseEntity<?> obtenerAsamblea(@RequestParam String codigo){

        try {
            return ResponseEntity.ok(asambleaS.obtenerAsamblea(codigo));
        }catch (TimeoutException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
