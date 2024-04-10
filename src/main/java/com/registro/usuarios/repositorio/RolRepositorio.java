package com.registro.usuarios.repositorio;
import com.registro.usuarios.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long>{

    public Rol findByNombre(String nombre);

}
