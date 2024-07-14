package com.remproyects.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

@NotNull
public record PacienteUpd(
        Long id,
        String nombre,
        String telefono
) { }
