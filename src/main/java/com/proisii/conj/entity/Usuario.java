package com.proisii.conj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

}
