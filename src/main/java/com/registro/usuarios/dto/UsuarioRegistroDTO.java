//DTO actualmente en desuso, si se implementan atributos no nulos y otras restricciones en modelo, se evalua si usar DTOs para mas entidades

package com.registro.usuarios.dto;


import lombok.Getter;
import lombok.Setter;

public class UsuarioRegistroDTO {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String nombre;

	@Getter
	@Setter
	private String apellido;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String contrasena;


	public UsuarioRegistroDTO(String nombre, String apellido, String email, String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = password;
	}

	public UsuarioRegistroDTO() {

	}

}
