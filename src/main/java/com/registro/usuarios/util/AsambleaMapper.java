package com.registro.usuarios.util;

import com.registro.usuarios.dto.AsambleaDTO;
import com.registro.usuarios.modelo.Asamblea;

public class AsambleaMapper {

    public static Asamblea mapAsambleaDTOToAsamblea(AsambleaDTO asambleaDTO){
        Asamblea asamblea = new Asamblea();

        return null;
    }

    public static AsambleaDTO mapAsambleaToAsambleaDTO(Asamblea asamblea){

        AsambleaDTO asambleaDTO = new AsambleaDTO();

        asambleaDTO.setIniciada(asamblea.getIniciada());
        asambleaDTO.setDescripcion(asamblea.getDescripcion());
        asambleaDTO.setFecha(asamblea.getFecha());
        asambleaDTO.setIdAsamblea(asamblea.getIdAsamblea());
        asambleaDTO.setHoraFinalizacion(asamblea.getHoraFinalizacion());
        asambleaDTO.setConjunto(asamblea.getConjunto().getId());
        asambleaDTO.setHoraInicio(asamblea.getHoraInicio());
        asambleaDTO.setPoderesMax(asamblea.getPoderesMax());
        asambleaDTO.setCodigoUnion(asamblea.getCodigoUnion());
        asambleaDTO.setNombreConjunto(asamblea.getConjunto().getNombre());

        return asambleaDTO;
    }
}
