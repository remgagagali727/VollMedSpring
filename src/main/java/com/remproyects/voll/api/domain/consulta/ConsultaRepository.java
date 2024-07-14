package com.remproyects.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByPacienteIdAndFechaBetween(Long aLong, LocalDateTime primerHora, LocalDateTime ultimaHora);

    Boolean existsByMedicoIdAndFecha(Long aLong, LocalDateTime fecha);
}
