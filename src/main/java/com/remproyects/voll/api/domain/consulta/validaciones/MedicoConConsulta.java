package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.domain.consulta.ConsultaRepository;
import com.remproyects.voll.api.domain.medico.MedicoRepository;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MedicoConConsulta implements ValidadorConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(ConsultaGenerarDatos datos) {

        LocalDateTime fecha = LocalDateTime.parse(datos.fecha());
        Boolean medicoConConsulta = consultaRepository
                .existsByMedicoIdAndFecha(datos.idMedico(), fecha);

        if(medicoConConsulta)
            throw new IntegrityValidationException("Este medico ya tiene una consulta");

    }
}
