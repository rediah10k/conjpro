package com.registro.usuarios.repositorio;

import com.registro.usuarios.dto.RespuestaDTO;
import com.registro.usuarios.modelo.Pregunta;
import com.registro.usuarios.modelo.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RespuestaRepositorio extends JpaRepository<Respuesta,Long> {

    List<Respuesta> findAllByPregunta(Pregunta pregunta);

    @Transactional
    @Modifying
    @Query("DELETE FROM Respuesta r WHERE r.respuesta = ?1 AND r.pregunta = ?2")
    void deleteByRespuestaAndPregunta(String respuesta, Pregunta pregunta);
}
