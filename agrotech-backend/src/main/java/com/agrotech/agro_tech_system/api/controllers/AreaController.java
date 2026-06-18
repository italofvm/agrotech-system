package com.agrotech.agro_tech_system.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import com.agrotech.agro_tech_system.api.dto.area.AreaResponseDTO;
import com.agrotech.agro_tech_system.api.dto.area.CriarAreaDTO;
import com.agrotech.agro_tech_system.application.usecase.area.AtualizarAreaUseCase;
import com.agrotech.agro_tech_system.application.usecase.area.CadastrarAreaUseCase;
import com.agrotech.agro_tech_system.application.usecase.area.DeletarAreaUseCase;
import com.agrotech.agro_tech_system.application.usecase.area.ListarAreasUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {

    private final CadastrarAreaUseCase cadastrarArea;
    private final ListarAreasUseCase listarAreas;
    private final AtualizarAreaUseCase atualizarArea;
    private final DeletarAreaUseCase deletarArea;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "criarArea")
    public ResponseEntity<Void> criar(@Valid @RequestBody CriarAreaDTO dto) {
        cadastrarArea.executar(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @Operation(operationId = "listarAreas")
    public ResponseEntity<List<AreaResponseDTO>> listar() {
        return ResponseEntity.ok(listarAreas.executar());
    }

    @GetMapping("/{id}")
    @Operation(operationId = "buscarAreaPorId")
    public ResponseEntity<AreaResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(listarAreas.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "atualizarArea")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @Valid @RequestBody CriarAreaDTO dto) {
        atualizarArea.executar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "deletarArea")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        deletarArea.execute(id);
        return ResponseEntity.noContent().build();
    }
}