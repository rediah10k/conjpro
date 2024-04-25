package com.registro.usuarios.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "preguntas")
public class Pregunta {

    @Id
    long idPregunta;
    String pregunta;

    @ManyToOne
    Encuesta idEncuesta;
}
