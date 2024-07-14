package com.remproyects.voll.api.domain.consulta;

import com.remproyects.voll.api.domain.consulta.validaciones.ValidadorConsulta;
import com.remproyects.voll.api.domain.medico.Medico;
import com.remproyects.voll.api.domain.medico.MedicoRepository;
import com.remproyects.voll.api.domain.paciente.Paciente;
import com.remproyects.voll.api.domain.paciente.PacienteRepository;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorConsulta> validadores;


    public Consulta agendar(ConsultaGenerarDatos datos) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(datos.idPaciente());
        if(pacienteOptional.isEmpty()) {
            throw new IntegrityValidationException("Este id de paciente no existe");
        }
        Paciente paciente = pacienteOptional.get();


        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new IntegrityValidationException("Este id de medico no existe");
        }

        if(datos.fecha() == null) {
            throw new IntegrityValidationException("Fecha invalida");
        }

        Medico medico = seleccionarMedico(datos);

        if(medico == null) {
            throw new IntegrityValidationException("No existe un medico disponible en la especialidad");
        }

        LocalDateTime fecha;

        try {
            fecha = LocalDateTime.parse(datos.fecha());
        } catch (DateTimeParseException e) {
            throw new IntegrityValidationException("Fecha invalida");
        }

        validadores.forEach(V -> V.validar(datos));

        System.out.println(medico);
        System.out.println(paciente);
        System.out.println(fecha);

        Consulta consulta = new Consulta(medico,
                paciente, fecha);

        consultaRepository.save(consulta);
        return consulta;
    }

    private Medico seleccionarMedico(ConsultaGenerarDatos datos) {
        Medico medicoOptional = null;
        if(datos.idMedico() != null)
            medicoOptional = medicoRepository.getReferenceById(datos.idMedico());
        if(medicoOptional != null) {
            return medicoOptional;
        } else {
            if(datos.especialidad() == null) {
                throw new IntegrityValidationException("La especialidad debe seleccionarse para asignar un medico");
            }
            Medico medico = medicoRepository.findByFechaPlusAviailableAndSpeciality(datos.especialidad(), LocalDateTime.parse(datos.fecha()));
            if(medico != null) {
                return medico;
            } else {
                throw new IntegrityValidationException("Medicos no disponibles");
            }
        }
    }

}
