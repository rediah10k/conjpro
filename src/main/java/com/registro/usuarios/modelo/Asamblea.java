package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "asamblea")
@AllArgsConstructor
@NoArgsConstructor
public class Asamblea {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer idAsamblea;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;
    private String nombreConjunto;
    private String descripcion;

    @Temporal(TemporalType.TIME)
    private LocalTime horaInicio;

    @Temporal(TemporalType.TIME)
    private LocalTime horaFinalizacion;

    @Column(nullable = false)
    private Boolean votoCoeficiente;

    @Column(nullable = false)
    private String poderesMax;

    @OneToMany(mappedBy = "asamblea")
    private List<Encuesta> encuestas;

    @OneToOne(mappedBy = "asamblea")
    private Planilla planillas;

    private String codigoUnion;

}
