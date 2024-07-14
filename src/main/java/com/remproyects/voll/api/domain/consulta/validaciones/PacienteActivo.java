package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.domain.paciente.PacienteRepository;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorConsulta{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(ConsultaGenerarDatos datos) {
        Boolean pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());
        if(!pacienteActivo)
            throw new IntegrityValidationException("El paciente no existe");
    }
}
