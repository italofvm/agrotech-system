package com.agrotech.agro_tech_system.api.dto.sensor;

import java.time.LocalDateTime;
import java.util.List;

public record SensorResponseDTO(
    String id,
    String nome,
    String localizacao,
    String tipo,
    boolean ativo,
    List<LeituraResponseDTO> leituras,
    List<HistoricoLocalizacaoDTO> historico

) {
    public record LeituraResponseDTO(
        Long id,
        Double valor,
        LocalDateTime dataHora,
        String localizacao
    ) { }

    public record HistoricoLocalizacaoDTO(
        String id,
        String localizacao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
    ) { }
}