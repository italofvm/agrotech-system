package com.agrotech.agro_tech_system.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agrotech.agro_tech_system.api.dto.leitura.LeituraRequestDTO;
import com.agrotech.agro_tech_system.api.dto.leitura.LeituraResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.leitura.BuscarTodasLeiturasUseCase;
import com.agrotech.agro_tech_system.application.usecase.leitura.RegistrarLeituraUseCase;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/leituras")
@RequiredArgsConstructor
public class LeituraController {

    private final RegistrarLeituraUseCase registrarLeitura;
    private final BuscarTodasLeiturasUseCase buscarTodasLeituras;

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody LeituraRequestDTO leitura) {
        registrarLeitura.executar(leitura.sensorId(), leitura.valor(), leitura.dataHora());
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<LeituraResponseDTO>> buscarTodas() {
        return ResponseEntity.ok(buscarTodasLeituras.executar());
    }
}
