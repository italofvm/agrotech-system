package com.agrotech.agro_tech_system.application.usecase.leitura;

import com.agrotech.agro_tech_system.api.dto.leitura.LeituraResponseDTO;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarTodasLeiturasUseCase {

    private final LeituraRepository leituraRepository;

    public List<LeituraResponseDTO> executar() {
        return leituraRepository.buscarTodas()
            .stream()
            .map(l -> new LeituraResponseDTO(
                l.getId(),
                l.getSensor().getId(),
                l.getSensor().getNome(),
                l.getValor(),
                l.getDataHora(),
                l.getLocalizacao()
            ))
            .toList();
    }
}
