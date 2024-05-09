package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface VotoRepository extends JpaRepository<Voto,Long> {

    @Query("select count(v) from Voto v where v.respuesta.idRespuesta = ?1")
    Integer obtenerConteoPorRespuesta(long idRespuesta);

}
