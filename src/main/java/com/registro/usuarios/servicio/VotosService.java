package com.registro.usuarios.servicio;

import com.registro.usuarios.dto.VotoDTO;
import com.registro.usuarios.modelo.Respuesta;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.modelo.Voto;
import com.registro.usuarios.repositorio.RespuestaRepositorio;
import com.registro.usuarios.repositorio.UsuarioRepositorio;
import com.registro.usuarios.repositorio.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotosService {

    private final VotoRepository votoRepository;
    private final UsuarioRepositorio usuarioRepositorio;
    private final RespuestaRepositorio respuestaRepositorio;

    public boolean guardarVotos(VotoDTO votoDTO){

        Usuario usuario = usuarioRepositorio.findByIdUsuario(Long.parseLong(votoDTO.getIdUsuario()));

        votoDTO.getRespuestasEncuesta().forEach(votoUsuario->{
            Voto voto = new Voto();

            Respuesta respuesta = respuestaRepositorio.findById(Long.parseLong(votoUsuario.getIdRespuesta())).orElseThrow();

            voto.setUsuario(usuario);
            voto.setRespuesta(respuesta);
            votoRepository.save(voto);
        });

        return true;
    }
}
