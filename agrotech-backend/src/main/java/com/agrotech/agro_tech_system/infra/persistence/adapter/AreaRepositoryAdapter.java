package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.repository.AreaRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.AreaEntity;
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
/*
    @Override
    public Area salvar(Area area) {
        AreaEntity entity;
        if (area.getId() != null) {
            entity = jpaAreaRepository.findById(area.getId())
                    .orElseGet(() -> PersistenceMapper.toEntity(area));
            entity.setNome(area.getNome());
            entity.setDescricao(area.getDescricao());
            entity.setAtivo(area.isAtivo());
        } else {
            entity = PersistenceMapper.toEntity(area);
        }
        var saved = jpaAreaRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }
*/
    
    @Override
    public Area salvar(Area area) {
        // Definimos explicitamente o tipo como AreaEntity
        AreaEntity entityParaSalvar;

        if (area.getId() != null && !area.getId().isBlank()) {
            entityParaSalvar = jpaAreaRepository.findById(area.getId())
                    .map(entityExistente -> {
                        entityExistente.setNome(area.getNome());
                        entityExistente.setDescricao(area.getDescricao());
                        return entityExistente;
                    })
                    .orElseGet(() -> PersistenceMapper.toEntity(area));
        } else {
            entityParaSalvar = PersistenceMapper.toEntity(area);
        }

        // Agora o compilador sabe que 'saved' é uma AreaEntity,
        // permitindo que o PersistenceMapper.toDomain(saved) funcione perfeitamente.
        AreaEntity saved = jpaAreaRepository.save(entityParaSalvar);
        
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

