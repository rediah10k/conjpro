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

    @ManyToOne
    @JoinColumn(name = "id_Asamblea")
    private Asamblea asamblea;

    @ManyToOne
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;

    private Boolean asistencia;
}
