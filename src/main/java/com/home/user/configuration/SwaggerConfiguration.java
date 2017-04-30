	package com.home.user.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile(value = { "production", "dev" })
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).
				groupName("user-service").
				apiInfo(apiInfo()).select()
				.paths(regex("/api/user.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User REST API")
				.description("User REST API reference")
				.license("Apache License Version 2.0")
				.version("2.0").build();
	}

}
