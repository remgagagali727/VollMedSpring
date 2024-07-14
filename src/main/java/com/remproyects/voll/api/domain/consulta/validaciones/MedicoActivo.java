package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorConsulta{

    @Autowired
    MedicoRepository medicoRepository;

    public void validar(ConsultaGenerarDatos datos){
        Boolean medicoActivo = medicoRepository.findActivoById(datos.idMedico());
    }
}
