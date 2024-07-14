package com.remproyects.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);
    @Query("""
            SELECT m FROM Medico m 
            WHERE m.activo = 1 
            AND m.especialidad = :especialidad 
            AND m.id NOT IN 
                (SELECT c.medico.id FROM Consulta c WHERE c.data = :fecha) 
            ORDER BY RAND() 
            LIMIT 1;
            """)
    Optional<Medico> findByFechaPlusAviailableAndSpeciality(Especialidad especialidad, LocalDateTime fecha);
}
