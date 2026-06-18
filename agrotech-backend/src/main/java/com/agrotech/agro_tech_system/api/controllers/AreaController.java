package com.agrotech.agro_tech_system.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Void> criar(@RequestBody CriarAreaDTO dto) {
        cadastrarArea.executar(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<AreaResponseDTO>> listar() {
        return ResponseEntity.ok(listarAreas.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(listarAreas.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody CriarAreaDTO dto) {
        atualizarArea.executar(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        deletarArea.execute(id);
        return ResponseEntity.noContent().build();
    }
}