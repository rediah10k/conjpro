package com.registro.usuarios.modelo;

import jakarta.persistence.*;

@Entity
public class Planilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistencia;

    // Relaciones
    @OneToOne
    @JoinColumn(name = "idAsamblea")
    private Asamblea asamblea;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    private Boolean asistencia;
}
