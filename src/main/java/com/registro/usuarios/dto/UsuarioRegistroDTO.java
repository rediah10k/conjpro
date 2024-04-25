//DTO actualmente en desuso, si se implementan atributos no nulos y otras restricciones en modelo, se evalua si usar DTOs para mas entidades

package com.registro.usuarios.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDTO {

	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private String contrasena;

}
