package com.registro.usuarios.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespuesta;
    private String respuesta;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "idEncuesta")
    private Encuesta encuesta;

    @OneToMany(mappedBy = "respuesta")
    private List<Voto> votos;
}
