package com.registro.usuarios.repositorio;
import com.registro.usuarios.modelo.Rol;
import com.registro.usuarios.modelo.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long>{

    Rol findByNombre(TipoRol nombre);

}
