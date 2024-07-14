package com.remproyects.voll.api.domain.paciente;

import com.remproyects.voll.api.domain.direccion.Direccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pacientes")
@Entity(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documentoIdentidad;
    private String telefono;

    private boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(PacienteGet paciente) {
        this.nombre = paciente.nombre();
        this.email = paciente.email();
        this.telefono = paciente.telefono();
        this.documentoIdentidad = paciente.documentoIdentidad();
        this.direccion = new Direccion(paciente.direccion());
        this.activo = true;
    }

    public void actualizarDatos(PacienteUpd pacienteUpd) {
        if(pacienteUpd.nombre() != null) nombre = pacienteUpd.nombre();
        if (pacienteUpd.telefono() != null) telefono = pacienteUpd.telefono();
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
