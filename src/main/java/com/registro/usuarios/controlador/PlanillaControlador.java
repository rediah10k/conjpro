package com.registro.usuarios.controlador;

import com.registro.usuarios.dto.PlanillaDTO;
import com.registro.usuarios.servicio.PlanillaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanillaControlador {

    private final PlanillaService planillaService;

    @GetMapping("/planilla")
    public ResponseEntity<PlanillaDTO> obtenerPlanilla(@RequestParam String codigo){
        return ResponseEntity.ok(planillaService.obtenerPlanillaDeAsamblea(codigo));
    }

    @GetMapping("/cerrarSesion")
    public ResponseEntity<?> cerrarSesion(@RequestParam String idUsuario,@RequestParam String codigoAsamblea){

        planillaService.ponerInasistencia(idUsuario,codigoAsamblea);

        return ResponseEntity.ok("Se ha cerrado la sesión");
    }

}
