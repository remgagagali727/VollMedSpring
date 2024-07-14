package com.remproyects.voll.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.remproyects.voll.api.domain.direccion.DireccionGet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties
public record MedicoGet(
    @NotBlank
    String nombre,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "\\d{4,5}")
    String documento,

    @NotBlank
    @Pattern(regexp = "\\d{9,11}")
    String telefono,

    @NotNull
    Especialidad especialidad,

    @NotNull
    @Valid
    DireccionGet direccion
) { }
