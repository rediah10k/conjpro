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

import java.util.List;

@Service
@RequiredArgsConstructor
public class VotosService {

    private final VotoRepository votoRepository;
    private final UsuarioRepositorio usuarioRepositorio;
    private final RespuestaRepositorio respuestaRepositorio;
    private final UsuarioServicio usuarioServicio;

    public boolean guardarVotos(VotoDTO votoDTO){

        List<Usuario> usuariosDelegados = usuarioServicio.consultarSiEsDelegado(votoDTO.getIdUsuario());

        if (!usuariosDelegados.isEmpty()){
            usuariosDelegados.forEach(usuarioDelegado -> {
                guardarVoto(votoDTO, usuarioDelegado);
            });
            return true;
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(Long.parseLong(votoDTO.getIdUsuario()));

        guardarVoto(votoDTO, usuario);

        return true;
    }

    private void guardarVoto(VotoDTO votoDTO, Usuario usuarioDelegado) {
        votoDTO.getRespuestasEncuesta().forEach(votoUsuario->{
            Voto voto = new Voto();

            Respuesta respuesta = respuestaRepositorio.findById(Long.parseLong(votoUsuario.getIdRespuesta())).orElseThrow();

            voto.setUsuario(usuarioDelegado);
            voto.setRespuesta(respuesta);
            votoRepository.save(voto);
        });
    }
}
