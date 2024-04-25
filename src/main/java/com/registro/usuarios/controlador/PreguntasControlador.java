package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.EncuestaDTO;
import com.registro.usuarios.servicio.EncuestaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encuesta")
public class PreguntasControlador {

    private final EncuestaServicio encuestaServicio;

    @GetMapping
    public ResponseEntity<?> preguntasPorAsamblea(EncuestaDTO encuesta){
        try{
            return ResponseEntity.ok(encuestaServicio.preguntasPorAsamblea(encuesta));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearEncuesta(@RequestBody EncuestaDTO encuestaDTO){

        try{
            return ResponseEntity.ok(encuestaServicio.crearEncuesta(encuestaDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
