package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.models.Usuario;
import com.agrotech.agro_tech_system.domain.repository.UsuarioRepository;
import com.agrotech.agro_tech_system.infra.persistence.mapper.PersistenceMapper;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return jpaUsuarioRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return jpaUsuarioRepository.findByLogin(login).map(PersistenceMapper::toDomain);
    }

    @Override
    public void salvar(Usuario usuario) {
        jpaUsuarioRepository.save(PersistenceMapper.toEntity(usuario));
    }

    @Override
    public void deletar(String id) {
        jpaUsuarioRepository.deleteById(id);
    }

    @Override
    public boolean existeLogin(String login) {
        return jpaUsuarioRepository.findByLogin(login).isPresent();
    }
}

