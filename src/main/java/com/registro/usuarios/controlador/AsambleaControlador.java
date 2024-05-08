package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.servicio.AsambleaServicio;
import javassist.NotFoundException;
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
        }catch (TimeoutException | NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/iniciarAsamblea")
    public ResponseEntity<?> iniciarAsamblea(@RequestParam String codigo){
        boolean asamblea = asambleaS.iniciarAsamblea(codigo);

        if (asamblea)
            return ResponseEntity.ok("Asamblea iniciada");

        return ResponseEntity.badRequest().body("No se pudo iniciar la asamblea");
    }

    @PutMapping("/activar_pregunta")
    public ResponseEntity<?> activarPregunta(@RequestParam String idPregunta){

        boolean activacion = asambleaS.activarPreguntar(idPregunta);

        if (activacion)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().body("Error al activar la pregunta");
    }

    @PutMapping("terminar_asamblea")
    public ResponseEntity<?> terminarAsamblea(@RequestParam String codigo){
        boolean termino = asambleaS.terminarAsamblea(codigo);

        if (termino)
            return ResponseEntity.ok("Asamblea terminada");

        return ResponseEntity.badRequest().body("No se pudo terminar la asamblea");

    }
}
