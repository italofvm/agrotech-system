package com.agrotech.agro_tech_system.application.usecase.regra;

import com.agrotech.agro_tech_system.api.dto.regra.RegraResponseDTO;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.RegraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarRegrasUseCase {

    private final RegraRepository  regraRepository;

    public List<RegraResponseDTO> executar(){
        return regraRepository.buscarTodas()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RegraResponseDTO buscarPorId(String id){
        var regra = regraRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Regra não encontrada!"));

        return mapToResponse(regra);
    }

    private RegraResponseDTO mapToResponse(Regra regra){
        return new RegraResponseDTO(
                regra.getId(),
                regra.getTipoSensor(),
                regra.getOperador(),
                regra.getValor(),
                regra.isAtivo(),
                regra.getMensagem()
        );
    }
}
