package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	public Usuario findByDocumento(Long doc);
	public Usuario findByIdUsuario(Long id);
	@Query("SELECT u FROM Usuario u WHERE u.externo=true")
	public List<Usuario> findAllByExterno();
	@Query("SELECT u FROM Usuario u WHERE u.externo=false")
	public List<Usuario> findAllByNoExterno();

	
}
