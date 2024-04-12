package com.registro.usuarios.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long idUsuario;


	@Getter
	@Setter
	@Column(name = "nombre")
	private String nombre;

	@Getter
	@Setter
	@Column(name = "apellido")
	private String apellido;

	@Getter
	@Setter
	@Column(name = "correo")
	private String correo;

	@Getter
	@Setter
	@Column(name = "documento")
	private Long documento;

	@Getter
	@Setter
	@Column(name = "contrasena")
	private String contrasena;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rol", referencedColumnName = "idRol")
	@Getter
	@Setter
	private Rol rol;

	@OneToMany(mappedBy = "usuario")
	private List<Propiedad> propiedades;



	public Usuario(Long id, String nombre, String apellido, String email, String contrasena) {
		super();
		this.idUsuario = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = email;
		this.contrasena = contrasena;
	}

	public Usuario(String nombre, String apellido, Long documento, String password, Rol rol) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.contrasena = password;
		this.rol=rol;
	}

	public Usuario() {

	}

}
