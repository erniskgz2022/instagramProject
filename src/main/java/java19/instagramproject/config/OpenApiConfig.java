package java19.instagramproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        /**
         * JWT Bearer Security Scheme
         *
         * Бул Swagger'ге:
         * - Authorization header колдонуларын
         * - типи HTTP экенин
         * - схемасы "bearer" экенин
         * - формат JWT экенин билдирет
         *
         * Натыйжада Swagger UI'да  Authorize кнопкасы чыгат
         */
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)   // HTTP authentication
                .scheme("bearer")                 // Bearer token схемасы
                .bearerFormat("JWT");             // Token форматы (JWT)

        /**
         * OpenAPI негизги конфигурациясы
         */
        return new OpenAPI()
                /**
                 * API жөнүндө жалпы маалымат
                 */
                .info(new Info()
                        .title("Instagram API")   // Swagger UI'дагы аталышы
                        .description("Instagram clone backend API documentation")
                        .version("1.0.0")         // API версиясы
                )

                /**
                 * Бул security requirement:
                 * - бардык endpoint'тер үчүн Bearer Auth талап кылынат
                 * - Swagger ар бир request'ке Authorization header кошот
                 */
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )

                /**
                 * SecurityScheme'ди OpenAPI'ге каттайбыз
                 * "bearerAuth" — бул схеманын аты
                 * SecurityRequirement'те да УШУЛ ат колдонулат
                 */
                .schemaRequirement("bearerAuth", bearerAuth);
    }
}
