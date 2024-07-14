package com.remproyects.voll.api.controller;

import com.remproyects.voll.api.domain.direccion.DireccionGet;
import com.remproyects.voll.api.domain.medico.*;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<MedicoGet> registrarMedico(@RequestBody @Valid MedicoGet medico,
                                          UriComponentsBuilder uriComponentsBuilder) {
        Medico medico1 = medicoRepository.save(new Medico(medico));
        MedicoGet medicoGet = new MedicoGet(
                medico1.getNombre(), medico1.getEmail(), medico1.getDocumento(), medico1.getTelefono(),
                medico1.getEspecialidad(),
                new DireccionGet(
                        medico1.getDireccion().getCalle(), medico1.getDireccion().getDistrito(),
                        medico1.getDireccion().getCiudad(), medico1.getDireccion().getNumero(),
                        medico1.getDireccion().getComplemento()
                )
        );
        //Codigo 201
        //Url donde se encuentra el medico
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico1.getId()).toUri();
        return ResponseEntity.created(url)
                .body(medico);
    }

    @GetMapping
    public ResponseEntity<Page<MedicoSend>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion)
                .map(MedicoSend::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid MedicoPut medicoPut) {
        Medico medico = medicoRepository.getReferenceById(medicoPut.id());
        medico.actualizarDatos(medicoPut);
        return ResponseEntity.ok(new MedicoGet(
                medico.getNombre(), medico.getEmail(), medico.getDocumento(), medico.getTelefono(),
                medico.getEspecialidad(),
                new DireccionGet(
                        medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable(name = "id") Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicoSend> retornaDatosMedico(@PathVariable(name = "id") Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        MedicoSend medicoSend = new MedicoSend(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(),
                medico.getDocumento(), medico.getEmail());
        return ResponseEntity.ok(medicoSend);
    }
}
