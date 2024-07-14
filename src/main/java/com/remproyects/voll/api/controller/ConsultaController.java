package com.remproyects.voll.api.controller;

import com.remproyects.voll.api.domain.consulta.AgendaDeConsultaService;
import com.remproyects.voll.api.domain.consulta.Consulta;
import com.remproyects.voll.api.domain.consulta.ConsultaDetallesDatos;
import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaDetallesDatos> agendar(@RequestBody @Valid ConsultaGenerarDatos datos) {
        service.agendar(datos);
        return ResponseEntity.ok(new ConsultaDetallesDatos(null,null,null,null));
    }

}
