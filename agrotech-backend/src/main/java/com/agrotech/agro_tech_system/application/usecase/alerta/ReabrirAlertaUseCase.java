package com.agrotech.agro_tech_system.application.usecase.alerta;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReabrirAlertaUseCase {

    private final AlertaRepository alertaRepository;

    public void executar(String id) {
        var alerta = alertaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Alerta não encontrado!"));
        if (alerta.isAtivo()) {
            throw new ValidacaoException("Alerta já está ativo!");
        }

        alerta.reabrir();
        alertaRepository.salvar(alerta);
    }
}
