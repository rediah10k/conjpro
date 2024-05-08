package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Planilla;
import com.registro.usuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlanillaRepositorio extends JpaRepository<Planilla,Long> {
    List<Planilla> findAllByAsamblea(Asamblea asamblea);

    @Transactional
    @Modifying
    @Query("UPDATE Planilla p SET p.asistencia = ?1 WHERE p.idAsistencia = ?2")
    void updateAsistencia(Boolean asistencia,Long idPlanilla);

    Planilla findByUsuarioAndAsamblea(Usuario idUsuario, Asamblea idAsamblea);

    @Transactional
    @Modifying
    @Query("update Planilla p set p.delegadoConectado = ?1 where p.usuario.idUsuario=?2")
    void updateByDelegadoConectado(boolean coneccion,Long idUsuario);


}
