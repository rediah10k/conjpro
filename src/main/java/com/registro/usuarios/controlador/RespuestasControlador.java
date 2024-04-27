package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.RespuestaDTO;
import com.registro.usuarios.servicio.RespuestasServicio;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
@RequiredArgsConstructor
public class RespuestasControlador {

    private final RespuestasServicio respuestasServicio;

    @PostMapping
    public ResponseEntity<?> guardarRespuestas(@RequestBody List<RespuestaDTO> respuestas){

        try {
            return ResponseEntity.ok(respuestasServicio.guardarRespuestas(respuestas));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarRespuesta(@RequestParam String respuesta,String idPregunta ){
            return ResponseEntity.ok(respuestasServicio.eliminarRespuesta(respuesta,idPregunta));
    }
}
