package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Conjunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConjuntoRepositorio extends JpaRepository<Conjunto, Long> {
}
