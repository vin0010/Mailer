package com.mailer.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(
				DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		List<VendorExtension> vendorExtensions = new ArrayList<VendorExtension>();
		
		ApiInfo apiInfo = new ApiInfo(
				"Mailer"
				, "Sending Email Async in Spring Boot usign kafka"
				, "1.0"
				, "https://github.com/vin0010/Mailer"
				, new Contact("Vinoth", "http://vin0010.github.io/vinoth", "\"vin10catchme@gmail.com\"")
				, "GNU General Public License v3.0"
				, "https://github.com/vin0010/Mailer/blob/master/LICENSE"
				, vendorExtensions);
		return apiInfo;
	}
}