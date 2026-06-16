package com.agrotech.agro_tech_system.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agrotech.agro_tech_system.api.dto.dashboard.DashboardResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.dashboard.BuscarDadosDashboardUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final BuscarDadosDashboardUseCase buscarDadosDashboard;

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> buscar() {
        return ResponseEntity.ok(buscarDadosDashboard.executar());
    }
}