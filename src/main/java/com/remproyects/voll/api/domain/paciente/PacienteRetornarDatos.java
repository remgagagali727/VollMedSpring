package com.remproyects.voll.api.domain.paciente;

import com.remproyects.voll.api.domain.direccion.DireccionGet;

public record PacienteRetornarDatos(
    Long id,
    String nombre,
    String email,
    String documento,
    String telefono,
    DireccionGet direccion
) {
    public PacienteRetornarDatos(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getEmail(), paciente.getDocumentoIdentidad(), new DireccionGet(paciente.getDireccion()));
    }
}
