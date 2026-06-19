package com.agrotech.agro_tech_system.api.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.v3.oas.annotations.Operation;
import com.agrotech.agro_tech_system.api.dto.alerta.AlertaResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.alerta.ListarAlertasUseCase;
import com.agrotech.agro_tech_system.application.usecase.alerta.ResolverAlertaUseCase;
import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.infra.sse.AlertaSseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final ListarAlertasUseCase listarAlertas;
    private final ResolverAlertaUseCase resolverAlerta;
    private final AlertaSseService alertaSseService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(operationId = "streamAlertas")
    public SseEmitter stream() {
        return alertaSseService.novoCliente();
    }

    @GetMapping
    @Operation(operationId = "listarAlertas")
    public ResponseEntity<List<AlertaResponseDTO>> listar(
            @RequestParam(required = false) StatusAlerta status) {

        if (status != null) {
            return ResponseEntity.ok(listarAlertas.porStatus(status));
        }

        return ResponseEntity.ok(listarAlertas.executar());
    }

    @PatchMapping("/{id}/resolver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> resolver(@PathVariable String id) {
        resolverAlerta.executar(id);
        return ResponseEntity.ok().build();
    }
}
