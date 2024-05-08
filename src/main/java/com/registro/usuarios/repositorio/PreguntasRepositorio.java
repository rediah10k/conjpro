package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Encuesta;
import com.registro.usuarios.modelo.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreguntasRepositorio extends JpaRepository<Pregunta,Long> {

    List<Pregunta> findAllByIdEncuesta(Encuesta encuesta);

    @Transactional
    @Modifying
    @Query("update Pregunta p set p.activada = true where p.idPregunta = ?1")
    void activarPregunta(Long idPregunta);
}
