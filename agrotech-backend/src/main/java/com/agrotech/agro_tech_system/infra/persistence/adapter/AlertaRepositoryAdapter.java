package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.repository.AlertaRepository;
import com.agrotech.agro_tech_system.infra.persistence.mapper.PersistenceMapper;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaAlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlertaRepositoryAdapter implements AlertaRepository {

    private final JpaAlertaRepository jpaAlertaRepository;

    @Override
    public Alerta salvar(Alerta alerta) {
        var saved = jpaAlertaRepository.save(PersistenceMapper.toEntity(alerta));
        return new Alerta(saved.getId(), alerta.getLeitura(), alerta.getRegra(), alerta.getStatus(), alerta.getDataHora());
    }

    @Override
    public Optional<Alerta> buscarPorId(String id) {
        return jpaAlertaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Alerta> buscarTodos() {
        return jpaAlertaRepository.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public List<Alerta> buscarPorStatus(StatusAlerta status) {
        return jpaAlertaRepository.findByStatus(status).stream().map(PersistenceMapper::toDomain).toList();
    }
}
