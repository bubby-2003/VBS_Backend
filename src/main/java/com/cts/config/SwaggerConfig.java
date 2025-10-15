package com.cts.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;

import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;

import io.swagger.v3.oas.models.security.SecurityScheme;

import io.swagger.v3.oas.models.Components;

@Configuration

public class SwaggerConfig {

	@Bean

	public OpenAPI makeOpenApi() {

		final String securitySchemeName = "BearerAuth";

		return new OpenAPI()

				.info(new Info()

						.title("Vehicle Service Booking Management System")

						.version("1.0")

						.description("It manages user's vehicle service booking of their respective vehicles")

						.contact(new Contact().name("Team D")))

				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

				.components(new Components()

						.addSecuritySchemes(securitySchemeName,

								new SecurityScheme()

										.name(securitySchemeName)

										.type(SecurityScheme.Type.HTTP)

										.scheme("bearer")

										.bearerFormat("JWT")));

	}

}
