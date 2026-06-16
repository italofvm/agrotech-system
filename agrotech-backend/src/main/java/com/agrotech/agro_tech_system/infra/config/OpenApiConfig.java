package com.agrotech.agro_tech_system.infra.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class OpenApiConfig {

	@Bean
	public OpenAPI customOpemAPI() {
		
		final String securitySchemeName = "beareAuth";
		
		return new OpenAPI()
				// 1ª Informações sobre a API
				.info(new Info()
						.title("AgroTech System API")
						.description("API para monitoramento inteligente de sensores agrícolas!")
						.version("1.0.0"))
				// 2ª Configurações de seguranã JWT no Swagger
				.addSecurityItem(
						new SecurityRequirement()
						.addList(securitySchemeName))
				// 3ª Definir o envio do Token
				.components(
						new Components()
						.addSecuritySchemes(securitySchemeName, 
								new SecurityScheme()
								.name(securitySchemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("Bearer")
								.bearerFormat("JWT")
								)
						);
	}
}
