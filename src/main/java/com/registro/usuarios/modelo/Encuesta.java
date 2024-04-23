package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="encuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEncuesta;

    @Column(nullable = false)
    private String pregunta;

    @ManyToOne
    @JoinColumn(name = "idAsamblea")
    private Asamblea asamblea;

    @OneToMany(mappedBy = "encuesta")
    private List<Respuesta> respuestas;

}
