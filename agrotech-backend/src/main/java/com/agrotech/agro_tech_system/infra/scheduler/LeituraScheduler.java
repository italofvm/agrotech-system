package com.agrotech.agro_tech_system.infra.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agrotech.agro_tech_system.application.usecase.leitura.GerarLeituraUseCase;

@Component
public class LeituraScheduler {

    private final GerarLeituraUseCase gerarLeituraUseCase;

    public LeituraScheduler(GerarLeituraUseCase gerarLeituraUseCase) {
        this.gerarLeituraUseCase = gerarLeituraUseCase;
    }

    @Scheduled(fixedRate = 10000)
    public void gerarLeituraAutomatica() {
        gerarLeituraUseCase.executar();
    }
}

