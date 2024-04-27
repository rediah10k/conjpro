package com.registro.usuarios.repositorio;


import com.registro.usuarios.modelo.Asamblea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AsambleaRepositorio extends JpaRepository<Asamblea, Integer> {

    Asamblea findByCodigoUnion(String id);

    @Transactional
    @Modifying
    @Query("update Asamblea a set a.iniciada = true where a.codigoUnion = ?1")
    void updateIniciada( String codigo);
}
