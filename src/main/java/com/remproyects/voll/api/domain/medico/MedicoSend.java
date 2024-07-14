package com.remproyects.voll.api.domain.medico;

public record MedicoSend(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email


) {


    public MedicoSend(Medico m) {
        this(m.getId(),m.getNombre(),m.getEspecialidad().toString(),m.getDocumento(),m.getEmail());
    }
}
