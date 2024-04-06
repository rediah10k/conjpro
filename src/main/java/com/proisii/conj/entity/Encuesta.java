package com.proisii.conj.entity;

import jakarta.persistence.*;

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


}
