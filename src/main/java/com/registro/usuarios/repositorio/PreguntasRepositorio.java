package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Encuesta;
import com.registro.usuarios.modelo.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreguntasRepositorio extends JpaRepository<Pregunta,Long> {

    List<Pregunta> findAllByIdEncuesta(Encuesta encuesta);
}
