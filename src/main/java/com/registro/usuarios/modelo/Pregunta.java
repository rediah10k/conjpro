package com.registro.usuarios.modelo;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idPregunta;
    String pregunta;

    @ManyToOne
    @JoinColumn(name = "id_encuesta")
    Encuesta idEncuesta;
}
