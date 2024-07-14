package com.remproyects.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DireccionGet(
    @NotBlank
    String calle,

    @NotBlank
    String distrito,

    @NotBlank
    String ciudad,

    @NotBlank
    String numero,

    @NotBlank
    String complemento
) {
    public DireccionGet(Direccion direccion) {
        this(direccion.getCalle(), direccion.getDistrito(), direccion.getCiudad(), direccion.getNumero(), direccion.getComplemento());
    }
}
