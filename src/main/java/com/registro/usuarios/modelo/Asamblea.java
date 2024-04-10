package com.registro.usuarios.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name= "asamblea")
public class Asamblea {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    @Column(name = "idAsamblea")
    private int idAsamblea;

    @Column(name = "fecha",nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    @Column(
            name = "horaInicio",
            nullable = false
    )
    @Temporal(TemporalType.TIME)
    private LocalTime horaInicio;

    @Column(
            name = "horaFinalizacion",
            nullable = false
    )
    @Temporal(TemporalType.TIME)

    private LocalTime horaFinalizacion;

    @Column(
            name = "votoCoeficiente",
            nullable = false
    )
    private boolean votoCoeficiente;

    @Column(
            name = "poderesMax",
            nullable = false
    )
    private int poderesMax;

    @OneToMany(mappedBy = "idEncuesta")
    private List<Encuesta> idEncuesta;


    @OneToOne(mappedBy = "asamblea")
    private Planilla planillas;

}
