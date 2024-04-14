package com.registro.usuarios.servicio;


import com.registro.usuarios.modelo.Asamblea;
import com.registro.usuarios.repositorio.AsambleaRepositorio;
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
