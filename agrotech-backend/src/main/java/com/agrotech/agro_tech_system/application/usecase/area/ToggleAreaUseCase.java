package com.agrotech.agro_tech_system.application.usecase.area;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToggleAreaUseCase {

    private final AreaRepository areaRepository;

    public void executar(String id) {
        Area area = areaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Área não encontrada!"));

        if (area.isAtivo()) {
            area.desativar();
        } else {
            area.ativar();
        }

        areaRepository.salvar(area);
    }
}
