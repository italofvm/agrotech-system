package com.agrotech.agro_tech_system.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.agrotech.agro_tech_system.api.dto.regra.RegraRequestDTO;
import com.agrotech.agro_tech_system.api.dto.regra.RegraResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.regra.AtivarDesativarRegraUseCase;
import com.agrotech.agro_tech_system.application.usecase.regra.CadastrarRegraUseCase;
import com.agrotech.agro_tech_system.application.usecase.regra.ListarRegrasUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/regras")
@RequiredArgsConstructor
public class RegraController {

    private final CadastrarRegraUseCase cadastrarRegra;
    private final ListarRegrasUseCase listarRegras;
    private final AtivarDesativarRegraUseCase ativarDesativarRegra;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> criar(@RequestBody RegraRequestDTO dto) {
        cadastrarRegra.executar(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<RegraResponseDTO>> listar() {
        return ResponseEntity.ok(listarRegras.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegraResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(listarRegras.buscarPorId(id));
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> ativar(@PathVariable String id) {
        ativarDesativarRegra.ativar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desativar(@PathVariable String id) {
        ativarDesativarRegra.desativar(id);
        return ResponseEntity.ok().build();
    }
}