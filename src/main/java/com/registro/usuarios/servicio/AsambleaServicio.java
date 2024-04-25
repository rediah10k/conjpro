package com.registro.usuarios.servicio;


import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Planilla;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.repositorio.ConjuntoRepositorio;
import com.registro.usuarios.repositorio.PlanillaRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.util.AsambleaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class AsambleaServicio {

    private final AsambleaRepositorio asambleaRepositorio;
    private final ConjuntoRepositorio conjuntoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PlanillaRepositorio planillaRepositorio;

    public Asamblea guardarAsamblea(AsambleaDTO asambleaDTO){

        Asamblea asamblea = new Asamblea();
        asamblea.setCodigoUnion(asambleaDTO.getCodigoUnion());
        asamblea.setFecha(asambleaDTO.getFecha());
        asamblea.setHoraInicio(asambleaDTO.getHoraInicio());
        asamblea.setHoraFinalizacion(asambleaDTO.getHoraFinalizacion());
        asamblea.setPoderesMax(asambleaDTO.getPoderesMax());
        asamblea.setDescripcion(asambleaDTO.getDescripcion());
        asamblea.setVotoCoeficiente(asambleaDTO.getVotoCoeficiente());

        asamblea.setConjunto(conjuntoRepositorio.findById(asambleaDTO.getConjunto()).orElse(null));

        return asambleaRepositorio.save(asamblea);
    }

    public String crearAsamblea(AsambleaDTO asamblea){
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
        Asamblea asambleaCreada = guardarAsamblea(asamblea);
        crearPlanilla(asambleaCreada);

        return asamblea.getCodigoUnion();
    }

    public void crearPlanilla(Asamblea asamblea){
        usuarioRepositorio.findAllByConjunto(asamblea.getConjunto()).forEach(usuario -> {
            Planilla planilla = new Planilla();
            planilla.setAsamblea(asamblea);
            planilla.setUsuario(usuario);
            planilla.setAsistencia(false);

            planillaRepositorio.save(planilla);
        });
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

    public AsambleaDTO obtenerAsamblea(String codigo) throws TimeoutException {
        Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigo);

        if (asamblea.getFecha().isBefore(LocalDate.now())){
            throw new TimeoutException("La asamblea ya no se encuentra disponible");
        }
        return AsambleaMapper.mapAsambleaToAsambleDTO(asamblea);
    }
}
