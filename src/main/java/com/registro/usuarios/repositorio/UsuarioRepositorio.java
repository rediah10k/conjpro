package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Conjunto;
import com.registro.usuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	Usuario findByDocumento(Long doc);
	Usuario findByIdUsuario(Long id);

	@Query("SELECT u FROM Usuario u WHERE u.externo=?1")
	List<Usuario> findAllByExterno(boolean externo);

	List<Usuario> findAllByConjunto(Conjunto conjunto);
}
