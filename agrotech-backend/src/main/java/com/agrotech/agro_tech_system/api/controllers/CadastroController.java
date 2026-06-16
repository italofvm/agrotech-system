package com.agrotech.agro_tech_system.api.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agrotech.agro_tech_system.api.dto.usuario.UsuarioRequestDTO;
import com.agrotech.agro_tech_system.application.usecase.usuario.CadastrarUsuarioUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cadastro")
@RequiredArgsConstructor
public class CadastroController {

    private final CadastrarUsuarioUseCase cadastro;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody UsuarioRequestDTO registro) {
        cadastro.executar(registro.login(), registro.senha(), registro.role());
        return ResponseEntity.status(201).build();
    }
}
