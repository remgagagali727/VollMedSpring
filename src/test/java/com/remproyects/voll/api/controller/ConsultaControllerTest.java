package com.remproyects.voll.api.controller;

import com.remproyects.voll.api.domain.consulta.AgendaDeConsultaService;
import com.remproyects.voll.api.domain.consulta.Consulta;
import com.remproyects.voll.api.domain.consulta.ConsultaDetallesDatos;
import com.remproyects.voll.api.domain.consulta.ConsultaGenerarDatos;
import com.remproyects.voll.api.domain.medico.Especialidad;
import com.remproyects.voll.api.domain.medico.Medico;
import com.remproyects.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ConsultaGenerarDatos> generarDatosJacksonTester;

    @Autowired
    private JacksonTester<ConsultaDetallesDatos> detallesDatosJacksonTester;

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Error 400")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given Valores iniciales
        MockHttpServletResponse response =  mvc.perform(post("/consultas")).andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Http 200")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given Valores iniciales

        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new ConsultaDetallesDatos(1l, 1l, 1l, fecha);
        var datosConsulta = new Consulta(new Medico(), new Paciente(), fecha);

        when(agendaDeConsultaService.agendar(any()))
                .thenReturn(datosConsulta);

        MockHttpServletResponse response =  mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generarDatosJacksonTester.write(
                        new ConsultaGenerarDatos(1l,1l,fecha.toString(), especialidad))
                        .getJson())
        ).andReturn().getResponse();

        var jsonEsperado = new ConsultaDetallesDatos(1l, 1l, 1l, fecha);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}