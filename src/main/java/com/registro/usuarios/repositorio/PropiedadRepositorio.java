package com.registro.usuarios.repositorio;


import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.modelo.Propiedad;
import com.registro.usuarios.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, Long> {
    public Rol findByIdPropiedad(Long id);
}
