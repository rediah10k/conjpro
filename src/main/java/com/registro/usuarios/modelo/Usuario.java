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
	@Column(name = "documento", unique = true)
	private Long documento;

	@Getter
	@Setter
	@Column(name = "contrasena")

	private String contrasena;
	@Getter
	@Setter
	@Column(name = "externo",nullable = true)
	private Boolean externo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rol", referencedColumnName = "idRol")
	@Getter
	@Setter
	private Rol rol;

	@OneToMany(mappedBy = "usuario")
	private List<Propiedad> propiedades;

	@OneToOne()
	@JoinColumn(name = "idApoderado", referencedColumnName = "idUsuario")
	@Getter
	@Setter
	private Usuario idApoderado;


	@OneToOne(mappedBy = "idApoderado")
	@Getter
	@Setter
	private Usuario apoderado;

	public Usuario(Long id, String nombre, String apellido, String email, String contrasena,Boolean externo) {
		super();
		this.idUsuario = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = email;
		this.contrasena = contrasena;
		this.externo=externo;
	}

	public Usuario(String nombre, String apellido, Long documento, String password,String correo ,Rol rol ,Boolean externo, Usuario apoderado) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.contrasena = password;
		this.correo = correo;
		this.rol=rol;
		this.externo=externo;
		this.apoderado=apoderado;

	}

	public Usuario() {


	}


}
