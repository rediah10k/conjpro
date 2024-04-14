package com.registro.usuarios.servicio;


import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
import com.registro.usuarios.repositorio.RolRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsambleaServicio {

    private AsambleaRepositorio asambleaRepositorio;
    public AsambleaServicio(AsambleaRepositorio asambleaRepositorio) {
        super();
        this.asambleaRepositorio = asambleaRepositorio;
    }
    public Asamblea guardarAsamblea(Asamblea asamblea){
        return asambleaRepositorio.save(asamblea);

    }
}
