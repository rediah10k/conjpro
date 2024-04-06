package com.proisii.conj.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column (name="idRol")
    private int idRol;

    @Column (name="rol")
    private String rol;

    @OneToOne
    @JoinColumn(name= "idRol")
    private Usuario idUser;


}
