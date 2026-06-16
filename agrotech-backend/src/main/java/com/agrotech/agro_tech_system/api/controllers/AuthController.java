package com.agrotech.agro_tech_system.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agrotech.agro_tech_system.api.dto.usuario.LoginRequestDTO;
import com.agrotech.agro_tech_system.api.dto.usuario.TokenResponseDTO;
import com.agrotech.agro_tech_system.application.usecase.usuario.AutenticarUsuarioUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutenticarUsuarioUseCase auth;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO logando) {
        String token = auth.executar(logando.login(), logando.senha());
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
