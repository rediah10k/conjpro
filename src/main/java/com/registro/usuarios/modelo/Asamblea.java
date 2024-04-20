package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name= "asamblea")
public class Asamblea {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    @Column(name = "idAsamblea")
    @Getter
    @Setter
    private Integer idAsamblea;

    @Column(name = "fecha",nullable = false)
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate fecha;

    @Getter
    @Setter
    private String nombreConjunto;

    @Getter
    @Setter
    private String descripcion;


    @Column(
            name = "horaInicio",
            nullable = true
    )
    @Temporal(TemporalType.TIME)
    @Getter
    @Setter
    private LocalTime horaInicio;

    @Column(
            name = "horaFinalizacion",
            nullable = true
    )
    @Temporal(TemporalType.TIME)
    @Getter
    @Setter
    private LocalTime horaFinalizacion;

    @Column(
            name = "votoCoeficiente",
            nullable = false
    )

    @Getter
    @Setter
    private Boolean votoCoeficiente;

    @Column(
            name = "poderesMax",
            nullable = false
    )

    @Getter
    @Setter
    private String poderesMax;

    @OneToMany(mappedBy = "asamblea")
    private List<Encuesta> encuestas;


    @OneToOne(mappedBy = "asamblea")
    private Planilla planillas;

    @Getter
    @Setter
    private String codigoUnion;

}
