package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropiedad;
    private String torreOManzana;
    private String casaOApartamento;
    private Float area;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
