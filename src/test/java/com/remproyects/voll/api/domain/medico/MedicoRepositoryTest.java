package com.remproyects.voll.api.domain.medico;

import com.remproyects.voll.api.domain.consulta.Consulta;
import com.remproyects.voll.api.domain.direccion.DireccionGet;
import com.remproyects.voll.api.domain.paciente.Paciente;
import com.remproyects.voll.api.domain.paciente.PacienteGet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retornar null cuando el medico se encuentra en consulta con otro paciente en ese horario")
    void findByFechaPlusAviailableAndSpecialityEscenario1() {

        LocalDateTime proximoLunes10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                .atTime(10, 0);

        Paciente paciente = registrarPaciente("Antonio", "a@gmail.com", "123123");
        Medico medico = registrarMedico("Jose", "j@gmail.com", "123456", Especialidad.CARDIOLOGIA);
        registrarConsulta(medico, paciente, proximoLunes10am);

        Medico medicoLibre = medicoRepository.findByFechaPlusAviailableAndSpeciality(Especialidad.CARDIOLOGIA, proximoLunes10am);

        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia de esta disponible")
    void findByFechaPlusAviailableAndSpecialityEscenario2() {

        LocalDateTime proximoLunes10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                .atTime(10, 0);

        Medico medico = registrarMedico("Jose", "j@gmail.com", "123456", Especialidad.CARDIOLOGIA);

        Medico medicoLibre = medicoRepository.findByFechaPlusAviailableAndSpeciality(Especialidad.CARDIOLOGIA, proximoLunes10am);

        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, paciente, medico, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private MedicoGet datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new MedicoGet(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private PacienteGet datosPaciente(String nombre, String email, String documento) {
        return new PacienteGet(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

    private DireccionGet datosDireccion() {
        return new DireccionGet(
                "a",
                "distrito 1",
                "ciudad 1",
                "11111",
                "a"
        );
    }

}