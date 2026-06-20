package com.agrotech.agro_tech_system.application.usecase.area;

import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarAreaUseCase {

    private final AreaRepository areaRepository;

    public void executar(String id){
        // Primeiro, garantimos que a área existe
        areaRepository.buscarPorId(id)
                .orElseThrow(() -> new ValidacaoException("Área não encontrada!"));

        // Tentamos deletar e capturamos erro de integridade do banco (FKs vinculadas)
        try {
            areaRepository.deletar(id);
        } catch (DataIntegrityViolationException e) {
            throw new ValidacaoException("Não é possível deletar esta área, pois existem sensores ou registros vinculados a ela.");
        }
    }
}
