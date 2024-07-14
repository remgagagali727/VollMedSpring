package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.domain.consulta.ConsultaRepository;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteSinConsulta implements ValidadorConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(ConsultaGenerarDatos datos) {
        LocalDateTime fecha = LocalDateTime.parse(datos.fecha());
        LocalDateTime primerHora = fecha.withHour(7);
        LocalDateTime ultimaHora = fecha.withHour(18);
        Boolean pacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHora, ultimaHora);
        if(pacienteConConsulta) {
            throw new IntegrityValidationException("Un paciente solo puede agendar citas una vez al dia");
        }
    }
}
