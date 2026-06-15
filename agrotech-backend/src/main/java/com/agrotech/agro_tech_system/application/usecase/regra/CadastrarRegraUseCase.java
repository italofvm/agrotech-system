package com.agrotech.agro_tech_system.application.usecase.regra;

import com.agrotech.agro_tech_system.api.dto.regra.RegraRequestDTO;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.RegraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarRegraUseCase {

    private final RegraRepository regraRepository;

    public Regra executar(RegraRequestDTO dto){
        Regra regra = new Regra(
                null,
                dto.tipoSensor(),
                dto.operador(),
                dto.valor(),
                true
        );
        return regraRepository.salvar(regra);
    }
}
