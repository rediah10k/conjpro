package com.registro.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncuestaDTO {

    private List<PreguntaDTO> preguntas;
    private String idEncuesta;
    private Integer idAsamblea;
}
