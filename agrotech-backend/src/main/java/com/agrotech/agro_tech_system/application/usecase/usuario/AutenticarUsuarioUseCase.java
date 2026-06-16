package com.agrotech.agro_tech_system.application.usecase.usuario;

import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;
import com.agrotech.agro_tech_system.infra.security.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {

    private final AuthenticationManager authManager;
    private final JpaUsuarioRepository jpaUsuarioRepo;
    private final TokenService tokenService;

    public String executar(String login, String senha){
        var authToken = new UsernamePasswordAuthenticationToken(login, senha);

        authManager.authenticate(authToken);

        UsuarioEntity usuario = jpaUsuarioRepo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return tokenService.gerarToken(usuario);
    }
}
