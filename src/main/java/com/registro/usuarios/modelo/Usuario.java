package com.registro.usuarios.modelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "correo")
	private String correo;

	@Column(name = "documento", unique = true)
	private long documento;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "externo")
	private boolean externo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();

	@OneToMany(mappedBy = "usuario")
	private List<Propiedad> propiedades;

	@OneToMany(mappedBy="delegado")
	private List<Usuario> delegantes;

	@JoinColumn(name = "delegadoIdUsuario", referencedColumnName = "idUsuario")
	@ManyToOne()
	private Usuario delegado;


}
