package com.remproyects.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findByActivoTrue(Pageable paginacion);
    @Override
    Optional<Paciente> findById(Long id);

    @Query("""
        select p.activo
        from Paciente p
        where p.id = :id
        """)
    Boolean findActivoById(Long id);
}
