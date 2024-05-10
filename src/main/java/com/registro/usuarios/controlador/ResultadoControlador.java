package com.registro.usuarios.controlador;

import com.registro.usuarios.servicio.ResultadoService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("resultado")
@RequiredArgsConstructor
public class ResultadoControlador {

    private final ResultadoService resultadoService;

    @GetMapping
    public ResponseEntity<?> obtenerResultadoAsamblea(@RequestParam String codigoAsamblea){
        try {
            return ResponseEntity.ok(resultadoService.obtenerResultadoAsamblea(codigoAsamblea));
        } catch (NotFoundException | TimeoutException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
