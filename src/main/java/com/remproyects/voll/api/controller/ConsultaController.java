package com.remproyects.voll.api.controller;

import com.remproyects.voll.api.domain.consulta.AgendaDeConsultaService;
import com.remproyects.voll.api.domain.consulta.Consulta;
import com.remproyects.voll.api.domain.consulta.ConsultaDetallesDatos;
import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaDetallesDatos> agendar(@RequestBody @Valid ConsultaGenerarDatos datos) {
        Consulta consulta = service.agendar(datos);
        return ResponseEntity.ok(new ConsultaDetallesDatos(consulta));
    }

}
