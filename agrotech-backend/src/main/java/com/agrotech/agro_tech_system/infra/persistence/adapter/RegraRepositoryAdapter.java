package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.repository.RegraRepository;
import com.agrotech.agro_tech_system.infra.persistence.mapper.PersistenceMapper;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaRegraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegraRepositoryAdapter implements RegraRepository {

    private final JpaRegraRepository jpaRegraRepository;

    @Override
    public Regra salvar(Regra regra) {
        var saved = jpaRegraRepository.save(PersistenceMapper.toEntity(regra));
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Regra> buscarPorId(String id) {
        return jpaRegraRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Regra> buscarTodas() {
        return jpaRegraRepository.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public List<Regra> buscarPorTipo(TipoSensor tipoSensor) {
        return jpaRegraRepository.findByTipoSensor(tipoSensor).stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public List<Regra> buscarAtivasPorTipo(TipoSensor tipoSensor) {
        return jpaRegraRepository.findByTipoSensorAndAtivoTrue(tipoSensor)
                .stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(String id) {
        jpaRegraRepository.deleteById(id);
    }
}

