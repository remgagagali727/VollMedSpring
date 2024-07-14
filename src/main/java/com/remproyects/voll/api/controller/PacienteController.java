package com.remproyects.voll.api.controller;

import com.remproyects.voll.api.domain.paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteGet> registrar(@RequestBody @Valid PacienteGet pacienteGet,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(pacienteGet));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url)
                .body(pacienteGet);
    }

    @GetMapping
    public ResponseEntity<Page<PacienteRetornarDatos>> listadoPacientes(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion)
                .map(PacienteRetornarDatos::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid PacienteUpd pacienteUpd) {
        Paciente paciente = pacienteRepository.getReferenceById(pacienteUpd.id());
        paciente.actualizarDatos(pacienteUpd);
        return ResponseEntity.ok(new PacienteGet(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable(name = "id") Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<PacienteGet> retornaDatosPaciente(@PathVariable(name = "id") Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        PacienteGet pacienteGet = new PacienteGet(paciente);
        return ResponseEntity.ok(pacienteGet);
    }

}
