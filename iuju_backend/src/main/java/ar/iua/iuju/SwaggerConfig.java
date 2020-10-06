package ar.iua.iuju;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ar.iua.iuju.business.utils.Constants;
import ar.iua.iuju.webservices.RESTConstants;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.protocols(new HashSet<>(Collections.singletonList(RESTConstants.HTTPS_PROTOCOL)))
				.consumes(new HashSet<>(Collections.singletonList(RESTConstants.CONTENT_TYPE_JSON)))
				.produces(new HashSet<>(Collections.singletonList(RESTConstants.CONTENT_TYPE_JSON)))
				.useDefaultResponseMessages(false)
				.tags(
					new Tag("Turnero", "Operations related to turnero in the API"),
					new Tag("Users", "Operations related to users in the API")
				)
				.select()
				.apis(RequestHandlerSelectors.basePackage("ar.fca.turnero"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Turnero API",
				"Backend API that give access to the Turnero endpoints",
				"1.0.0",
				"",
				new Contact("Walter Gabriel Perez Sardi, Alejandro Capitanio",
						"https://gitlab.fcalatam.com/fca-ar/human-resources/turnero-m-dico",
						"walter.perezsardi@fcagroup.com, alejandro.capitanio@fcagroup.com "),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0",
				Collections.emptyList()
				);
	}

}