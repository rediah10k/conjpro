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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rol")
public class Rol {

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TipoRol nombre;
	@Id
	private Long id;

}
