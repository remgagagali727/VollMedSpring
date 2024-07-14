package com.remproyects.voll.api.domain.paciente;

import com.remproyects.voll.api.domain.direccion.DireccionGet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PacienteGet(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String telefono,
        @NotBlank String documentoIdentidad,
        @NotNull @Valid DireccionGet direccion
) {
    public PacienteGet(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
                paciente.getDocumentoIdentidad(), new DireccionGet(paciente.getDireccion()));
    }
}
