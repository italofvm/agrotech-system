package com.agrotech.agro_tech_system.infra.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;

public class BeansConfig {
	@Bean
	Clock clock() {
		return Clock.systemDefaultZone();
	}
}
