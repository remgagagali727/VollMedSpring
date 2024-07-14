package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAntisipacion implements ValidadorConsulta {
    public void validar(ConsultaGenerarDatos datos){
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fecha = LocalDateTime.parse(datos.fecha());
        Boolean diferencia = ahora.isAfter(fecha) || Duration.between(ahora,fecha).toMinutes() < 30;
        if(diferencia)
            throw new IntegrityValidationException("Las consultas deben de tener al menos 30 minutos de anticipacion");
    }
}
