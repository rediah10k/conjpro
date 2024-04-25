package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Conjunto;
import com.registro.usuarios.repositorio.ConjuntoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConjuntoService {

    private final ConjuntoRepositorio conjuntoRepositorio;

    public List<Conjunto> obtenerConjuntos() {
        return conjuntoRepositorio.findAll();
    }
}
