package com.proisii.conj.entity;

import jakarta.persistence.*;

@Entity
@Table(name="voto")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idVoto")
    private int idVoto;


}
