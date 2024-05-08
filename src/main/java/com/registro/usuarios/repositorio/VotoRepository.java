package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto,Long> {

}
