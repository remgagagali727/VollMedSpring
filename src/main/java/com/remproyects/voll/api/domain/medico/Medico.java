package com.remproyects.voll.api.domain.medico;

import com.remproyects.voll.api.domain.direccion.Direccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String documento;

    private String telefono;

    private Boolean activo;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;


    public Medico(@Valid MedicoGet medico) {
        this.nombre = medico.nombre();
        this.email = medico.email();
        this.documento = medico.documento();
        this.especialidad = medico.especialidad();
        this.direccion = new Direccion(medico.direccion());
        this.telefono = medico.telefono();
        this.activo = true;
    }

    public void actualizarDatos(MedicoPut medicoPut) {
        if(medicoPut.nombre()!=null)
            this.nombre = medicoPut.nombre();
        if (medicoPut.documento()!=null)
            this.documento = medicoPut.documento();
        if(medicoPut.direccion()!=null)
            this.direccion = direccion.actualizarDatos(medicoPut.direccion());
    }

    public void desactivarMedico() {
        this.activo = false;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", documento='" + documento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", activo=" + activo +
                ", especialidad=" + especialidad +
                ", direccion=" + direccion +
                '}';
    }
}
