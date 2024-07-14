package com.remproyects.voll.api.domain.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultaDetallesDatos(
    Long id,
    Long idPaciente,
    Long idMedico,
    LocalDateTime fecha
) {
    public ConsultaDetallesDatos(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(),
                consulta.getMedico().getId(), consulta.getFecha());
    }
}
