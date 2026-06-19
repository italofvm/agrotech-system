package com.agrotech.agro_tech_system.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean OpenAPI customOpemAPI() {
		
		final String securitySchemeName = "beareAuth";
		
		return new OpenAPI()
			.info(new Info()
				.title("AgroTech System API")
				.description("API para monitoramento inteligente de sensores agrícolas!")
				.version("1.0.0"))
			.addSecurityItem(new SecurityRequirement()
				.addList(securitySchemeName))
			.components(new Components()
				.addSecuritySchemes(securitySchemeName, new SecurityScheme()
					.name(securitySchemeName)
					.type(SecurityScheme.Type.HTTP)
					.scheme("Bearer")
					.bearerFormat("JWT"))
			);
	}
}
