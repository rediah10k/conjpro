package com.registro.usuarios.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="encuesta")
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="idEncuesta")
    private int idEncuesta;

    @Column(
            name = "pregunta",
            nullable = false
    )
    private String pregunta;

    @ManyToOne
    @JoinColumn(name = "idAsamblea")
    private Asamblea idAsamblea;


    @OneToMany(mappedBy = "encuesta")
    private List<Respuesta> respuestas;


}
