package com.registro.usuarios.servicio;


import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.dto.EncuestaDTO;
import com.registro.usuarios.dto.PreguntaDTO;
import com.registro.usuarios.dto.RespuestaDTO;
import com.registro.usuarios.exceptions.NoFechaException;
import com.registro.usuarios.modelo.*;
import com.registro.usuarios.repositorio.*;
import com.registro.usuarios.util.AsambleaMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final EncuestaRepositorio encuestaRepositorio;
    private final RespuestaRepositorio respuestaRepositorio;
    private final PreguntasRepositorio preguntasRepositorio;
    private final UsuarioServicio usuarioServicio;

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

    public Asamblea guardarAsamblea(AsambleaDTO asambleaDTO){

        Asamblea asamblea = new Asamblea();
        asamblea.setCodigoUnion(asambleaDTO.getCodigoUnion());
        asamblea.setFecha(asambleaDTO.getFecha());
        asamblea.setHoraInicio(asambleaDTO.getHoraInicio());
        asamblea.setHoraFinalizacion(asambleaDTO.getHoraFinalizacion());
        asamblea.setPoderesMax(asambleaDTO.getPoderesMax());
        asamblea.setDescripcion(asambleaDTO.getDescripcion());
        asamblea.setIniciada(false);

        asamblea.setConjunto(conjuntoRepositorio.findById(asambleaDTO.getConjunto()).orElse(null));

        return asambleaRepositorio.save(asamblea);
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

    public String ingresarAsamblea(String code,String idUsuario, Model model){
        if (code.length() != 6) {
            model.addAttribute("error", "El código debe tener exactamente 6 dígitos.");
            return "ingresarAsamblea";
        }

        Asamblea aEncontrada = asambleaRepositorio.findByCodigoUnion(code);

        Usuario usuario = usuarioRepositorio.findByIdUsuario(Long.valueOf(idUsuario));

        List<Usuario> usuariosDelegados = usuarioServicio.consultarSiEsDelegado(String.valueOf(usuario.getIdUsuario()));

        if ( !usuariosDelegados.isEmpty()){

            for (Usuario usuarioDelegado : usuariosDelegados) {
                planillaRepositorio.updateByDelegadoConectado(true, usuarioDelegado.getIdUsuario());
                Planilla idPlanilla = planillaRepositorio.findByUsuarioAndAsamblea(usuarioDelegado, aEncontrada);

                if (aEncontrada == null ||aEncontrada.getFecha().isBefore(LocalDate.now()) || !aEncontrada.getConjunto().getNombre().equals(usuarioDelegado.getConjunto().getNombre()) || aEncontrada.getHoraFinalizacion()!=null) {
                    model.addAttribute("error", "No se encontró una asamblea con ese código.");
                    return "ingresarAsamblea";
                }

                if (idPlanilla.getAsistencia()){
                    model.addAttribute("error", "Ya se encuentra conectado el propietario "+usuarioDelegado.getNombre()+" "+usuarioDelegado.getApellido()+", por favor solicite que se desconecte.");
                    return "ingresarAsamblea";
                }

                planillaRepositorio.updateAsistencia(true, idPlanilla.getIdAsistencia());
            }

        }

          Planilla idPlanilla  = planillaRepositorio.findByUsuarioAndAsamblea(usuario,aEncontrada);

          if (!usuario.isExterno()){

              if (idPlanilla.isDelegadoConectado())
              {
                  model.addAttribute("error", "Ya se encuentra conectado el delegado designado.");
                  return "ingresarAsamblea";
              }

              if (aEncontrada == null ||aEncontrada.getFecha().isBefore(LocalDate.now()) || !aEncontrada.getConjunto().getNombre().equals(usuario.getConjunto().getNombre()) || aEncontrada.getHoraFinalizacion()!=null) {
                  model.addAttribute("error", "No se encontró una asamblea con ese código.");
                  return "ingresarAsamblea";
              }

              planillaRepositorio.updateAsistencia(true, idPlanilla.getIdAsistencia());
          }

        model.addAttribute("mensaje", "Validación exitosa, esperando inicio de la asamblea.");
        return "ingresarAsamblea";
    }

    public AsambleaDTO obtenerAsamblea(String codigo) throws TimeoutException, NotFoundException {
        Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigo);
        if (asamblea == null){
            throw new NotFoundException("La asamblea no existe");
        }


        Encuesta encuesta = encuestaRepositorio.findByAsamblea(asamblea);

        EncuestaDTO encuestaDTO = new EncuestaDTO();
        encuestaDTO.setPreguntas(preguntasRepositorio.findAllByIdEncuesta(encuesta).stream().map(pregunta -> {
            PreguntaDTO preguntaDTO = new PreguntaDTO();
            preguntaDTO.setPregunta(pregunta.getPregunta());
            preguntaDTO.setIdPregunta(String.valueOf(pregunta.getIdPregunta()));
            preguntaDTO.setActivada(pregunta.isActivada());
            preguntaDTO.setRespuestas(
                    respuestaRepositorio.findAllByPregunta(pregunta).stream().map(
                            respuesta -> {
                                RespuestaDTO respuestaDTO = new RespuestaDTO();
                                respuestaDTO.setRespuesta(respuesta.getRespuesta());
                                 respuestaDTO.setIdRespuesta(String.valueOf(respuesta.getIdRespuesta()));
                                return respuestaDTO;
                            }
                    ).toList()
            );
            return preguntaDTO;
        }).toList());

        AsambleaDTO asambleaDTO = AsambleaMapper.mapAsambleaToAsambleaDTO(asamblea);
        asambleaDTO.setEncuesta(encuestaDTO);

        return asambleaDTO;
    }

    public boolean iniciarAsamblea(String codigo) throws NoFechaException {
        try {

            Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigo);

            if (asamblea.getFecha().isEqual(LocalDate.now())){
                asambleaRepositorio.updateIniciada(codigo);
            }
            else{
                throw new NoFechaException();
            }
        }
        catch (Exception e){
            throw new NoFechaException("No se puede iniciar la asamblea porque no es la fecha programada");
        }

        return true;
    }

    public boolean activarPreguntar(String idPregunta){
        try {
            preguntasRepositorio.activarPregunta(Long.parseLong(idPregunta));
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean terminarAsamblea(String codigo){
        try {
            Asamblea asamblea = asambleaRepositorio.findByCodigoUnion(codigo);

            asamblea.setHoraFinalizacion(LocalTime.now());
            asambleaRepositorio.save(asamblea);

            List<Usuario> delegados = usuarioRepositorio.encontrarDelegadosPorConjunto(asamblea.getConjunto());

            usuarioRepositorio.updateDelegadosUsuarios(asamblea.getConjunto());

            usuarioRepositorio.deleteAll(delegados);

        }catch (Exception e){
            return false;
        }

        return true;
    }
}
