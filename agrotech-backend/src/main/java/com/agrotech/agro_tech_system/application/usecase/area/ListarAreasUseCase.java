package com.agrotech.agro_tech_system.application.usecase.area;

import com.agrotech.agro_tech_system.api.dto.area.AreaResponseDTO;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAreasUseCase {

    private final AreaRepository areaRepository;

    public List<AreaResponseDTO> executar(){
        return areaRepository.buscarTodos()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AreaResponseDTO buscarPorId(String id){
        var area = areaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Área não encontrada!"));

        return mapToResponse(area);
    }

    private AreaResponseDTO mapToResponse(Area area){
        return new AreaResponseDTO(
                area.getId(),
                area.getNome(),
                area.getDescricao()
        );
    }
}
