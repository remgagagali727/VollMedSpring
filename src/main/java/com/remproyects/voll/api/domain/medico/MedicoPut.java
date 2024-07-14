package com.remproyects.voll.api.domain.medico;

import com.remproyects.voll.api.domain.direccion.DireccionGet;
import jakarta.validation.constraints.NotNull;

public record MedicoPut(
    @NotNull
    Long id,
    String nombre,
    String documento,
    DireccionGet direccion
) { }
