package com.registro.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaDTO {
    private String pregunta;
    private String idPregunta;
    private boolean votoCoeficiente;
    private List<RespuestaDTO> respuestas;
    private boolean activada;
}
