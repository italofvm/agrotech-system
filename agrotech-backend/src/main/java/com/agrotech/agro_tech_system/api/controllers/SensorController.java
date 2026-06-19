package com.agrotech.agro_tech_system.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import com.agrotech.agro_tech_system.api.dto.sensor.AtualizarSensorDTO;
import com.agrotech.agro_tech_system.api.dto.sensor.CriarSensorDTO;
import com.agrotech.agro_tech_system.api.dto.sensor.SensorResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.sensor.AtualizarSensorUseCase;
import com.agrotech.agro_tech_system.application.usecase.sensor.CadastrarSensorUseCase;
import com.agrotech.agro_tech_system.application.usecase.sensor.DeletarSensores;
import com.agrotech.agro_tech_system.application.usecase.sensor.ListarSensoresUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sensores")
@RequiredArgsConstructor
public class SensorController {

    private final CadastrarSensorUseCase cadastrarSensor;
    private final ListarSensoresUseCase listarSensores;
    private final AtualizarSensorUseCase atualizarSensor;
    private final DeletarSensores deletarSensor;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(operationId = "criarSensor")
    public ResponseEntity<Void> criar(@Valid @RequestBody CriarSensorDTO criarSensor) {
        cadastrarSensor.executar(criarSensor);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @Operation(operationId = "listarSensores")
    public ResponseEntity<List<SensorResponseDTO>> listar() {
        return ResponseEntity.ok(listarSensores.executar());
    }

    @GetMapping("/{id}")
    @Operation(operationId = "buscarSensorPorId")
    public ResponseEntity<SensorResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(listarSensores.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "atualizarSensor")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id,
            @RequestBody AtualizarSensorDTO atualizar) {

        atualizarSensor.executar(id, atualizar.nome());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "deletarSensor")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        deletarSensor.executar(id);
        return ResponseEntity.noContent().build();
    }
}
