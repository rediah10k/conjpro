package com.registro.usuarios.controlador;

import com.registro.usuarios.servicio.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resultado")
@RequiredArgsConstructor
public class ResultadoControlador {

    private final ResultadoService resultadoService;

    @GetMapping
    public ResponseEntity<?> obtenerResultadoAsamblea(@RequestParam String codigoAsamblea){
        return ResponseEntity.ok(resultadoService.obtenerResultadoAsamblea(codigoAsamblea));
    }
}
