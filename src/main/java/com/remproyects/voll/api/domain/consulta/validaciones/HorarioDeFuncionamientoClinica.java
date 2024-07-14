package com.remproyects.voll.api.domain.consulta.validaciones;

import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorConsulta {
    public void validar(ConsultaGenerarDatos datos) {
        LocalDateTime fecha = LocalDateTime.parse(datos.fecha());
        Boolean domingo = DayOfWeek.SUNDAY.equals(fecha.getDayOfWeek());
        Boolean antesDeAbrir = fecha.getHour() < 7;
        Boolean despuesDeCerrar = fecha.getHour() > 19;
        if(domingo || antesDeAbrir || despuesDeCerrar) {
            throw new IntegrityValidationException("El horario de atencion de la clinica es de lunes a sabado" +
                    "de 07:00 a 19:00 hrs");
        }
    }
}
