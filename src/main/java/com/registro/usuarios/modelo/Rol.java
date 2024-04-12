package com.registro.usuarios.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long idRol;

	@Getter
	@Setter
	private String nombre;

	@OneToMany(mappedBy = "rol")
	private List<Usuario> usuarios;

	public Rol() {

	}

	public Rol(Long id, String nombre) {
		super();
		this.idRol=id;
		this.nombre = nombre;
	}


}
