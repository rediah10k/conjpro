package com.proisii.conj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuario {

    @Id
    @Column(name = "id_usuario")
    Long id;

    @Getter
    @Column(name = "contrasena")
    String contrasena;

    @Column(name = "nombre")
    String nombre;

    @Column(name = "usuario")
    Integer usuario;

    @OneToOne(mappedBy = "idUser")
    private Rol idRol;
}
