package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.PlanillaDTO;
import com.registro.usuarios.dto.UsuarioDTO;
import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Planilla;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.repositorio.PlanillaRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanillaService {

    private final PlanillaRepositorio planillaRepositorio;
    private final AsambleaRepositorio asambleaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioServicio usuarioServicio;

    public void ponerInasistencia(String idUsuario, String codigoAsamblea) {

        if (idUsuario == null || codigoAsamblea == null)
            return;

        Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigoAsamblea);
        Usuario usuario = usuarioRepositorio.findById(Long.valueOf(idUsuario)).orElse(null);

        Usuario usuarioDelegado = usuarioServicio.consultarSiEsDelegado(idUsuario);

        if (asamblea == null || asamblea.getFecha().isBefore(LocalDate.now()))
            return;

        if (usuarioDelegado != null) {
            planillaRepositorio.updateByDelegadoConectado(false, usuarioDelegado.getIdUsuario());
            usuario = usuarioDelegado;
        }

        Planilla planilla = planillaRepositorio.findByUsuarioAndAsamblea(usuario, asamblea);

        planillaRepositorio.updateAsistencia(false, planilla.getIdAsistencia());

    }

    public PlanillaDTO obtenerPlanillaDeAsamblea(String codigo) {

        Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigo);

        if (asamblea == null)
            return null;

        List<Planilla> planillas = planillaRepositorio.findAllByAsamblea(asamblea);

        PlanillaDTO planillaDTO = new PlanillaDTO();
        planillaDTO.setIdAsamblea(asamblea.getIdAsamblea().toString());
        planillaDTO.setUsuarios(planillas.stream().map(registro -> {

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombre(registro.getUsuario().getNombre());
            usuarioDTO.setCorreo(registro.getUsuario().getCorreo());
            usuarioDTO.setAsistencia(registro.getAsistencia());
            usuarioDTO.setApellido(registro.getUsuario().getApellido());

            return usuarioDTO;
        }).toList());

        return planillaDTO;
    }
}
