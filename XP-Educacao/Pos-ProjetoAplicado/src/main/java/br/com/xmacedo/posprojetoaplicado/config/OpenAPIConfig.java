package br.com.xmacedo.posprojetoaplicado.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Controle do BPM Camunda (POS-XPE) ")
                        .version("1.0")
                        .description("API controle e orchestração de BPM para transações e validação anti-fraude.")
                        .contact(new Contact()
                                .name("Felipe Macedo")
                                .email("felipe.x.macedo@gmail.com")));
    }
}
