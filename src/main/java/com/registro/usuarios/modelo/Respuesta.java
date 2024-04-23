package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
