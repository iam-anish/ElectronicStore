package com.lcwd.electronicstore2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());

        docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSchemes()));

        ApiSelectorBuilder select = docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());
        Docket build = select.build();

        return build;
    }

    private SecurityContext getSecurityContext() {
        SecurityContext context = SecurityContext.builder()
                .securityReferences(getSecurityReference())
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReference() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("Global","Access EveryThings")
        };
        return Arrays.asList(new SecurityReference("JWT",scopes));
    }

    private ApiKey getSchemes() {
        return new ApiKey("JWT","Authorization","header");
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Project Backend : APIS",
                "This is backend project created by Anish Mathakiya",
                "1.0.0V",
                "https://www.nldnslvn.com",
                new Contact("Anish Mathakiya","https://www.linkedin.com/in/anish-mathakiya-53063a216/","anumathakiya073@gmail.com"),
                "License of APIS",
                "https://www.nldnslvn.com",
                new ArrayList<>()
        );
        return apiInfo;
    }
}
