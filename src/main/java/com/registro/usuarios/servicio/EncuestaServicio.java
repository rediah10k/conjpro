package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.EncuestaDTO;
import com.registro.usuarios.dto.PreguntaDTO;
import com.registro.usuarios.dto.RespuestaDTO;
import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Encuesta;
import com.registro.usuarios.modelo.Pregunta;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.repositorio.EncuestaRepositorio;
import com.registro.usuarios.repositorio.PreguntasRepositorio;
import com.registro.usuarios.repositorio.RespuestaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncuestaServicio {

    private final EncuestaRepositorio encuestaRepositorio;
    private final PreguntasRepositorio preguntasRepositorio;
    private final AsambleaRepositorio asambleaRepositorio;
    private final RespuestaRepositorio respuestaRepositorio;

    public Encuesta crearEncuesta(EncuestaDTO encuestaDTO) {

        Encuesta encuestaExistente = encuestaRepositorio.findByAsamblea(asambleaRepositorio.findById(encuestaDTO.getIdAsamblea()).get());

        if (encuestaExistente == null) {
            encuestaExistente = new Encuesta();
            encuestaExistente.setAsamblea(asambleaRepositorio.findById(encuestaDTO.getIdAsamblea()).get());
            encuestaRepositorio.save(encuestaExistente);
        }

        Encuesta finalEncuestaExistente = encuestaExistente;
        encuestaDTO.getPreguntas().stream().map(preguntaDTO -> {
            Pregunta pregunta = new Pregunta();
            pregunta.setPregunta(preguntaDTO.getPregunta());
            pregunta.setIdEncuesta(finalEncuestaExistente);
            pregunta.setVotoCoeficiente(preguntaDTO.isVotoCoeficiente());
            return pregunta;
        }).forEach(preguntasRepositorio::save);

        return encuestaExistente;
    }

    public EncuestaDTO preguntasPorAsamblea(EncuestaDTO encuestaDTO) {
        Asamblea asamblea = asambleaRepositorio.findById(encuestaDTO.getIdAsamblea()).get();

        Encuesta encuesta = encuestaRepositorio.findByAsambleaAndIdEncuesta(asamblea, Long.parseLong(encuestaDTO.getIdEncuesta()));

        List<Pregunta> preguntas = preguntasRepositorio.findAllByIdEncuesta(encuesta);

        EncuestaDTO encuestaObtenida = new EncuestaDTO();

        encuestaObtenida.setPreguntas(
                preguntas.stream().map(pregunta -> {

                    PreguntaDTO preguntaDTO = new PreguntaDTO();
                    preguntaDTO.setPregunta(pregunta.getPregunta());
                    preguntaDTO.setIdPregunta(String.valueOf(pregunta.getIdPregunta()));

                    return preguntaDTO;
        }).toList());

        encuestaObtenida.setIdAsamblea(encuesta.getAsamblea().getIdAsamblea());
        encuestaObtenida.setIdEncuesta(String.valueOf(encuesta.getIdEncuesta()));

        return encuestaObtenida;
    }
}


