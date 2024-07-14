package com.remproyects.voll.api.domain.consulta;

import com.remproyects.voll.api.domain.medico.Medico;
import com.remproyects.voll.api.domain.medico.MedicoRepository;
import com.remproyects.voll.api.domain.paciente.Paciente;
import com.remproyects.voll.api.domain.paciente.PacienteRepository;
import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;


    public void agendar(ConsultaGenerarDatos datos) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(datos.idPaciente());
        if(pacienteOptional.isEmpty()) {
            throw new IntegrityValidationException("Este id de paciente no existe");
        }
        Paciente paciente = pacienteOptional.get();


        if(datos.idMedico() == null || medicoRepository.existsById(datos.idMedico())) {
            throw new IntegrityValidationException("Este id no existe");
        }
        Medico medico = seleccionarMedico(datos);


        LocalDateTime fecha = LocalDateTime.parse(datos.fecha());

        Consulta consulta = new Consulta(medico,
                paciente, fecha);

        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(ConsultaGenerarDatos datos) {
        Optional<Medico> medicoOptional = medicoRepository.findById(datos.idMedico());
        if(medicoOptional.isPresent()) {
            return medicoOptional.get();
        } else {
            if(datos.especialidad() == null) {
                throw new IntegrityValidationException("La especialidad debe seleccionarse para asignar un medico");
            }
            Optional<Medico> medicoOptional1 = medicoRepository.findByFechaPlusAviailableAndSpeciality(datos.especialidad(), LocalDateTime.parse(datos.fecha()));
            if(medicoOptional1.isPresent()) {
                return medicoOptional1.get();
            } else {
                throw new IntegrityValidationException("Medicos no disponibles");
            }
        }
    }

}
