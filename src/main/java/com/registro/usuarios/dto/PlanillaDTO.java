package com.registro.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PlanillaDTO {

    private List<UsuarioDTO> usuarios;
    private String idAsamblea;
}
