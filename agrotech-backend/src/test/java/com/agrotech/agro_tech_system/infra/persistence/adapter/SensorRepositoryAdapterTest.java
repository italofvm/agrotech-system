package com.agrotech.agro_tech_system.infra.persistence.adapter;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import com.agrotech.agro_tech_system.domain.exception.ValidacaoException;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaAreaRepository;
import com.agrotech.agro_tech_system.infra.persistence.repository.JpaSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorRepositoryAdapterTest {

    @Mock
    private JpaSensorRepository jpaSensorRepository;

    @Mock
    private JpaAreaRepository jpaAreaRepository;

    @InjectMocks
    private SensorRepositoryAdapter adapter;

    @Test
    void deveLancarValidacaoQuandoNaoExistirAreaParaNovoSensor() {
        Sensor novoSensor = new Sensor(null, "Sensor 1", "Estufa A", true, TipoSensor.TEMPERATURA);

        when(jpaSensorRepository.findById("")).thenReturn(Optional.empty());
        when(jpaAreaRepository.findAll()).thenReturn(List.of());

        ValidacaoException ex = assertThrows(ValidacaoException.class, () -> adapter.salvar(novoSensor));
        assertEquals("Cadastre ao menos uma area antes de criar sensores", ex.getMessage());
    }
}

