package com.registro.usuarios.servicio;


import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AsambleaServicio {

    private final AsambleaRepositorio asambleaRepositorio;

    public Asamblea guardarAsamblea(Asamblea asamblea){
        return asambleaRepositorio.save(asamblea);
    }

    public String crearAsamblea(Asamblea asamblea){
        Integer codigo = 0;
        Boolean existCodigo = true;
        do {
            codigo = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
            Asamblea AsambleaExt = asambleaRepositorio.findByCodigoUnion(codigo.toString());
            if (AsambleaExt == null || AsambleaExt.getFecha().isBefore(LocalDate.now())) {
                existCodigo = false;
            }
        } while (existCodigo);
        asamblea.setCodigoUnion(String.valueOf(codigo));
        guardarAsamblea(asamblea);

        return asamblea.getCodigoUnion();
    }

    public String ingresarAsamblea(String code, Model model){
        if (code.length() != 6) {
            model.addAttribute("error", "El código debe tener exactamente 6 dígitos.");
            return "ingresarAsamblea";
        }

        Asamblea aEncontrada = asambleaRepositorio.findByCodigoUnion(code);
        if (aEncontrada == null ||aEncontrada.getFecha().isBefore(LocalDate.now())) {
            model.addAttribute("error", "No se encontró una asamblea con ese código.");
            return "ingresarAsamblea";
        }

        model.addAttribute("mensaje", "Validación exitosa, esperando inicio de la asamblea.");
        return "ingresarAsamblea";
    }
}
