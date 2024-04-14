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

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class AsambleaControlador {

        @Autowired
        private AsambleaRepositorio asambleaR;
        @Autowired
        private AsambleaServicio asambleaS;
    //@Role(1)
    @GetMapping("/crearAsamblea")
    public String crearAsamblea(Model model) {

        Asamblea asamblea = new Asamblea();
        asamblea.setVotoCoeficiente(false);
        model.addAttribute("asamblea", asamblea);

        return "crearAsamblea";
    }

    @PostMapping("/crearAsamblea")

    public ResponseEntity<String> crearAsamblea(@RequestBody Asamblea asamblea) {
        Integer codigo = 0;
        Boolean existCodigo = true;
        do {
            codigo = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
            Asamblea AsambleaExt = asambleaR.findByCodigoUnion(codigo.toString());
            if (AsambleaExt == null || AsambleaExt.getFecha().isBefore(LocalDate.now())) {
                existCodigo = false;
            }
        } while (existCodigo);
        asamblea.setCodigoUnion(String.valueOf(codigo));
        asambleaS.guardarAsamblea(asamblea);
        return ResponseEntity.ok(asamblea.getCodigoUnion());
    }

}
