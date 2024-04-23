package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
