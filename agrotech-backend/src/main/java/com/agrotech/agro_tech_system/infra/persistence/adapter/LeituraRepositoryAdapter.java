package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.repository.LeituraRepository;
import com.agrotech.agro_tech_system.infra.persistence.entity.LeituraEntity;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaLeituraRepository;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeituraRepositoryAdapter implements LeituraRepository {

    private final JpaLeituraRepository jpaLeituraRepository;
    private final JpaSensorRepository jpaSensorRepository;

    @Override
    public Leitura salvar(Leitura leitura) {
        var sensor = jpaSensorRepository.findById(leitura.getSensor().getId())
            .orElseThrow(() -> new IllegalStateException("Sensor não encontrado para persistir leitura"));

        LeituraEntity entity = new LeituraEntity(leitura.getId(),
            sensor, leitura.getValor(), leitura.getDataHora(),
            leitura.getLocalizacao());

        LeituraEntity salva = jpaLeituraRepository.save(entity);
        return new Leitura(salva.getId(), leitura.getSensor(),
            salva.getValor(), salva.getDataHora(), salva.getLocalizacao());
    }

    @Override
    public List<Leitura> buscarPorSensor(String sensorId) {
        return jpaLeituraRepository.findBySensorId(sensorId)
            .stream()
            .map(entity -> new Leitura(entity.getId(),
                new Sensor(entity.getSensor().getId(),
                    entity.getSensor().getNome(),
                    entity.getSensor().getLocalizacao(),
                    true,
                    entity.getSensor().getTipo()
                ),
                entity.getValor(),
                entity.getDataHora(),
                entity.getLocalizacao()
            ))
            .toList();
    }

    @Override
    public List<Leitura> buscarTodas() {
        return jpaLeituraRepository.findAll(Sort.by(Sort.Direction.DESC, "dataHora"))
            .stream()
            .map(entity -> new Leitura(entity.getId(),
                new Sensor(entity.getSensor().getId(),
                    entity.getSensor().getNome(),
                    entity.getSensor().getLocalizacao(),
                    true,
                    entity.getSensor().getTipo()
                ),
                entity.getValor(),
                entity.getDataHora(),
                entity.getLocalizacao()
            ))
            .toList();
    }
}

