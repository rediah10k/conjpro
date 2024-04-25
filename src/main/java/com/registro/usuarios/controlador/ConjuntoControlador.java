package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Conjunto;
import com.registro.usuarios.servicio.ConjuntoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conjuntos")
public class ConjuntoControlador {

    private final ConjuntoService conjuntoService;

    @GetMapping
    public List<Conjunto> obtenerConjuntos() {
        return conjuntoService.obtenerConjuntos();
    }
}
