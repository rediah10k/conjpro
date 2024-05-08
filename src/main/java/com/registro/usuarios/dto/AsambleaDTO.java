package com.registro.usuarios.dto;

import com.registro.usuarios.modelo.Conjunto;
import com.registro.usuarios.modelo.Encuesta;
import com.registro.usuarios.modelo.Planilla;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AsambleaDTO {

    private Integer idAsamblea;
    private LocalDate fecha;
    private long conjunto;
    private String nombreConjunto;
    private String descripcion;
    private LocalTime horaInicio;
    private LocalTime horaFinalizacion;
    private String poderesMax;
    private String planillaId;
    private String codigoUnion;
    private EncuestaDTO encuesta;
    private boolean iniciada;
}
