package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Conjunto;
import com.registro.usuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	Usuario findByDocumento(Long doc);
	Usuario findByIdUsuario(Long id);

	@Query("SELECT u FROM Usuario u WHERE u.externo=?1")
	List<Usuario> findAllByExterno(boolean externo);

	List<Usuario> findAllByConjunto(Conjunto conjunto);

	@Query("select u from Usuario u where u.delegado.idUsuario = ?1")
	Usuario findByDelegado(Long idDelegado);

	@Transactional
	@Modifying
	@Query("delete from Usuario u where u.externo = true and u.conjunto is null and u.idUsuario = ?1")
	void eliminarUsuarioExternoNoDelegado(Long idUsuario);

	List<Usuario> findAllByDelegado(Usuario delegado);

	@Query("select u from Usuario u where u.idUsuario in (select u.delegado.idUsuario from Usuario u where u.delegado.idUsuario != 0 and u.conjunto = ?1 and u.externo = true)")
	List<Usuario> encontrarDelegadosPorConjunto(Conjunto conjunto);

	@Transactional
	@Modifying
	@Query("update Usuario u set u.delegado = null where u.delegado.idUsuario != 0 and u.conjunto = ?1")
	void updateDelegadosUsuarios(Conjunto conjunto);
}
