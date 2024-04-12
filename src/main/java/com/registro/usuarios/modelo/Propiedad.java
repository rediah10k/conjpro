package com.registro.usuarios.modelo;

import jakarta.persistence.*;

@Entity
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropiedad;
    private String torreOManzana;
    private String casaOApartamento;
    private Float area;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
