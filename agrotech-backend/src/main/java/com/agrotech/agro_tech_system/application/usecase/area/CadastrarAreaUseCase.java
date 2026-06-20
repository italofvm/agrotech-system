package com.agrotech.agro_tech_system.application.usecase.area;


import com.agrotech.agro_tech_system.api.dto.area.CriarAreaDTO;
import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarAreaUseCase {

    private final AreaRepository areaRepository;

    public Area executar(CriarAreaDTO dto){
        Area area = new Area(
                null,
                dto.nome(),
                dto.descricao(),
                true
        );

        return areaRepository.salvar(area);
    }
}
