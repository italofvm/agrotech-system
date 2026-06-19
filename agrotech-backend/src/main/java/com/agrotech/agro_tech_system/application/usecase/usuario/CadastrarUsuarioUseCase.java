package com.agrotech.agro_tech_system.application.usecase.usuario;

import com.agrotech.agro_tech_system.domain.enums.UserRole;
import com.agrotech.agro_tech_system.domain.exception.RegraNegocioException;
import com.agrotech.agro_tech_system.domain.models.Usuario;
import com.agrotech.agro_tech_system.domain.repository.UsuarioRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private final UsuarioRepository repoDomain;

    private final PasswordEncoder passwordEncoder;

    public void executar(String login, String senha, @NotNull UserRole userRole){
        if(repoDomain.existeLogin(login)){
            throw new RegraNegocioException("Este email/login já está em uso!");
        }

        String senhaCriptografada = passwordEncoder.encode(senha);

        Usuario usuario = new Usuario(
          null, login, senhaCriptografada, userRole
        );

        repoDomain.salvar(usuario);
    }
}
