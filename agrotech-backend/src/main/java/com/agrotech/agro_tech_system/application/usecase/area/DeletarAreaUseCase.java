package com.agrotech.agro_tech_system.application.usecase.area;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarAreaUseCase {

    private final AreaRepository areaRepository;

    public void execute(String id){
        areaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Área não encontrada!"));
    }
}
