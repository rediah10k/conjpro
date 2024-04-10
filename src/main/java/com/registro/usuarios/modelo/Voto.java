package com.registro.usuarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name="voto")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoto;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idEncuesta")
    private Encuesta encuesta;

    @ManyToOne
    @JoinColumn(name = "idRespuesta")
    private Respuesta respuesta;


}
