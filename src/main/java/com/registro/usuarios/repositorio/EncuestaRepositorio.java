package com.registro.usuarios.repositorio;

import com.registro.usuarios.dto.EncuestaDTO;
import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncuestaRepositorio extends JpaRepository<Encuesta,Long> {

    Encuesta findByAsambleaAndIdEncuesta(Asamblea asamblea, Long idEncuesta);
    Encuesta findByAsamblea(Asamblea asamblea);
}
