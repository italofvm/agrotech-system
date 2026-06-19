package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.SensorEntity;
import com.agrotech.agro_tech_system.infra.persistence.mapper.PersistenceMapper;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaAreaRepository;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SensorRepositoryAdapter implements SensorRepository {

    private final JpaSensorRepository jpaSensorRepository;
    private final JpaAreaRepository jpaAreaRepository;

    @Override
    public Sensor salvar(Sensor sensor) {
        SensorEntity entity = jpaSensorRepository.findById(sensor.getId() == null ? "" : sensor.getId())
                .orElseGet(() -> {
                    var areaPadrao = jpaAreaRepository.findAll().stream().findFirst()
                            .orElseThrow(() -> new ValidacaoException("Cadastre ao menos uma area antes de criar sensores"));
                    return new SensorEntity(null, null, null, false, null, areaPadrao, null);
                });

        entity.setNome(sensor.getNome());
        entity.setLocalizacao(sensor.getLocalizacao());
        entity.setTipo(sensor.getTipo());
        entity.setAtivo(sensor.isAtivo());

        SensorEntity salvo = jpaSensorRepository.save(entity);
        return PersistenceMapper.toDomain(salvo);
    }

    @Override
    public Optional<Sensor> buscarPorId(String id) {
        return jpaSensorRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Sensor> buscarTodos() {
        return jpaSensorRepository.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public void deletar(String id) {
        jpaSensorRepository.deleteById(id);
    }
}

