package org.egov.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("org.egov")).paths(PathSelectors.any()).build()
            .apiInfo(apiInfo()); // Completely Optional
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("All API")
            .description("API details of each service").version("1.0").build();
    }
}
