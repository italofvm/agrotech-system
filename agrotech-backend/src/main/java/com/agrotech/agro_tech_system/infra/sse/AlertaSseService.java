package com.agrotech.agro_tech_system.infra.sse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AlertaSseService {

    // CopyOnWriteArrayList garante thread-safety entre o Scheduler e as conexões HTTP
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter novoCliente() {
        // Long.MAX_VALUE mantém a conexão aberta indefinidamente
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(()    -> emitters.remove(emitter));
        emitter.onError(e       -> emitters.remove(emitter));

        emitters.add(emitter);
        return emitter;
    }

    public void notificar(Object payload) {
        List<SseEmitter> mortos = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(payload, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                mortos.add(emitter);
            }
        }

        emitters.removeAll(mortos);
    }
}
