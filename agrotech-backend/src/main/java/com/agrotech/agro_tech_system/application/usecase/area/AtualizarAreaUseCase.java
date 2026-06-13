package com.agrotech.agro_tech_system.application.usecase.area;

import com.agrotech.agro_tech_system.api.dto.area.CriarAreaDTO;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarAreaUseCase {

    private final AreaRepository areaRepository;

    public void executar(String id, CriarAreaDTO dto) {
        var area = areaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Área não encontrada!"));

        area.alterarDescricao(dto.descricao());

        areaRepository.salvar(area);
    }
}
