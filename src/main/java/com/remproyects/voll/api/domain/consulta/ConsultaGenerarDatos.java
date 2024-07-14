package com.remproyects.voll.api.domain.consulta;

import com.remproyects.voll.api.domain.medico.Especialidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaGenerarDatos(
    @NotNull Long idPaciente,
    Long idMedico,
    @NotBlank String fecha,
    Especialidad especialidad
) { }
