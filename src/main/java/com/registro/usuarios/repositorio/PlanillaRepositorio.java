package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Planilla;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanillaRepositorio extends JpaRepository<Planilla,Long> {
}
