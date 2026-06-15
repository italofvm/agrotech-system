package com.agrotech.agro_tech_system.application.usecase.regra;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.RegraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtivarDesativarRegraUseCase {

    private final RegraRepository regraRepository;

    public void ativar(String id) {
        var regra = regraRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Regra não encontrada!"));
        regra.ativar();
        regraRepository.salvar(regra);
    }

    public void desativar(String id) {
        var regra = regraRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Regra não encontrada!"));

        regra.desativar();
        regraRepository.salvar(regra);
    }
}
