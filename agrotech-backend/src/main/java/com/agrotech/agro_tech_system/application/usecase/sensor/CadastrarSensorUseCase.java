package com.agrotech.agro_tech_system.application.usecase.sensor;

import com.agrotech.agro_tech_system.api.dto.sensor.CriarSensorDTO;
import com.agrotech.agro_tech_system.application.usecase.leitura.GerarLeituraUseCase;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.models.SensorLocalizacao;
import com.agrotech.agro_tech_system.domain.repository.SensorLocalizacaoRepository;
import com.agrotech.agro_tech_system.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastrarSensorUseCase {

    private final SensorRepository sensorRepository;
    private final GerarLeituraUseCase gerarLeituraUseCase;
    private final SensorLocalizacaoRepository sensorLocalizacaoRepository;

    public Sensor executar(CriarSensorDTO criarSensorDTO) {
        Sensor novoSensor = new Sensor(
                null,
                criarSensorDTO.nome(),
                criarSensorDTO.localizacao(),
                true,
                criarSensorDTO.tipo()
        );

        // Salva o sensor em sua própria transação (Spring Data JPA @Transactional)
        // para que o INSERT seja commitado antes de gerar a primeira leitura.
        Sensor sensorSalvo = sensorRepository.salvar(novoSensor);

        // Registra o histórico de localização inicial
        sensorLocalizacaoRepository.salvar(new SensorLocalizacao(
                null,
                sensorSalvo.getId(),
                sensorSalvo.getLocalizacao(),
                LocalDateTime.now(),
                null
        ));

        // Gera a primeira leitura fake em uma transação separada.
        // Falhas aqui não impedem a criação do sensor — o scheduler gerará
        // leituras no próximo ciclo de qualquer forma.
        try {
            gerarLeituraUseCase.gerarParaSensor(sensorSalvo);
        } catch (Exception e) {
            // Leitura inicial será gerada pelo scheduler em até 10 segundos
        }

        return sensorSalvo;
    }
}
