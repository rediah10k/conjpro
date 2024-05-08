package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.VotoDTO;
import com.registro.usuarios.servicio.VotosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("votos")
public class VotosControlador {

    private final VotosService votosService;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarVotos(@RequestBody VotoDTO votoDTO){
        return ResponseEntity.ok(votosService.guardarVotos(votoDTO));
    }
}
