package com.registro.usuarios.repositorio;


import com.registro.usuarios.modelo.Asamblea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsambleaRepositorio extends JpaRepository<Asamblea, Integer> {

    public Asamblea findByIdAsamblea(Integer id);
    public Asamblea findByCodigoUnion(String id);
}
