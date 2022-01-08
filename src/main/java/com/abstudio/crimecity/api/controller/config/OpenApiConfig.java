package com.abstudio.crimecity.api.controller.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Space Crime - Player API",
        description = "Space Crime, Player API Swagger", version = "v1"),
        servers = {
                @Server(url = "/")
        })
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        scheme = "bearer"
)

public class OpenApiConfig {
    private static final String CORRELATION_ID = "x-correlation-id";

    // To easily add a global parameter to every component check out: https://github.com/springdoc/springdoc-openapi/issues/39
    @Bean
    public OpenAPI customOpenAPI() {
        StringSchema schema = new StringSchema();
        return new OpenAPI()
                .components(new Components().addParameters(CORRELATION_ID, new HeaderParameter().required(false)
                        .name(CORRELATION_ID).description("The correlation ID for your query").schema(schema)));
    }

//    @Bean
//    public OpenApiCustomiser consumerTypeHeaderOpenAPICustomiser() {
//        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
//                .forEach(operation -> operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/" + CORRELATION_ID)));
//    }
}
