package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.RespuestaDTO;
import com.registro.usuarios.modelo.Encuesta;
import com.registro.usuarios.modelo.Pregunta;
import com.registro.usuarios.modelo.Respuesta;
import com.registro.usuarios.repositorio.EncuestaRepositorio;
import com.registro.usuarios.repositorio.PreguntasRepositorio;
import com.registro.usuarios.repositorio.RespuestaRepositorio;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespuestasServicio {

    private final RespuestaRepositorio respuestaRepositorio;
    private final PreguntasRepositorio preguntasRepositorio;

    public boolean guardarRespuestas(List<RespuestaDTO> respuestas) throws NotFoundException {

        respuestaRepositorio.saveAll(respuestas.stream().map(respuestaDTO -> {
            Pregunta pregunta = preguntasRepositorio.findById(Long.valueOf(respuestaDTO.getIdPregunta())).orElse(null);

            if (pregunta == null)
                try {
                    throw  new NotFoundException("Error al intentar guardar las respuestas, no existe pregunta");
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
            Respuesta respuesta = new Respuesta();
            respuesta.setRespuesta(respuestaDTO.getRespuesta());
            respuesta.setPregunta(pregunta);
            return respuesta;
        }).toList());

        return true;
    }

    public boolean eliminarRespuesta(String respuesta,String idPregunta){

        Pregunta pregunta = preguntasRepositorio.findById(Long.valueOf(idPregunta)).orElse(null);
        if (pregunta == null)
            return false;

        respuestaRepositorio.deleteByRespuestaAndPregunta(respuesta,pregunta);

        return true;
    }
}
