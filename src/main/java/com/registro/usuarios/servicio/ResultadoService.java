package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.dto.EncuestaDTO;
import com.registro.usuarios.dto.ResultadoDTO;
import com.registro.usuarios.repositorio.VotoRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Service
public class ResultadoService {

    private final AsambleaServicio asambleaServicio;
    private  final VotoRepository votoRepository;

    public List< ResultadoDTO> obtenerResultadoAsamblea(String codigoAsamblea){

        List<ResultadoDTO> resultados = new ArrayList<>();

        try {
            AsambleaDTO asambleaDTO = asambleaServicio.obtenerAsamblea(codigoAsamblea);

            EncuestaDTO encuesta = asambleaDTO.getEncuesta();

            encuesta.getPreguntas().forEach(pregunta -> {
                ResultadoDTO resultadoDTO = new ResultadoDTO();
                resultadoDTO.setPregunta(pregunta.getPregunta());

                Map<String,Integer> resultadosMap = new HashMap<>();

                pregunta.getRespuestas().forEach(respuesta -> {
                    resultadosMap.put(respuesta.getRespuesta(),votoRepository.obtenerConteoPorRespuesta(Long.parseLong(respuesta.getIdRespuesta())));
                });

                resultadoDTO.setResultados(resultadosMap);

                resultados.add(resultadoDTO);
            });

        } catch (TimeoutException | NotFoundException e) {
            throw new RuntimeException(e);
        }

        return resultados;
    }

}
