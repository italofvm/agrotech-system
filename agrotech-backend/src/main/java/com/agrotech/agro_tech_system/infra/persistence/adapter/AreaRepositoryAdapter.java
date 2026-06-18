package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import com.agrotech.agro_tech_system.infra.persistence.mapper.PersistenceMapper;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AreaRepositoryAdapter implements AreaRepository {

    private final JpaAreaRepository jpaAreaRepository;

    @Override
    public Area salvar(Area area) {
        var saved = jpaAreaRepository.save(PersistenceMapper.toEntity(area));
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Area> buscarPorId(String id) {
        return jpaAreaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Area> buscarTodos() {
        return jpaAreaRepository.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public void deletar(String id) {
        jpaAreaRepository.deleteById(id);
    }
}

