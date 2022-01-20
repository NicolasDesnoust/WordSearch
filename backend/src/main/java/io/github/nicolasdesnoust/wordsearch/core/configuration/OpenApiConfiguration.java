package io.github.nicolasdesnoust.wordsearch.core.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${word-search.name}")
                    String name,
            @Value("${word-search.version}")
                    String version,
            @Value("${word-search.description}")
                    String description
    ) {
        var info = new Info()
                .title(name)
                .version(version)
                .description("API Rest d'une a" + description.substring(1)
                        + " Vous pouvez en savoir plus à propos de la spécification Open Api 3 ici: [https://swagger.io/specification/](https://swagger.io/specification/)");

        return new OpenAPI().info(info);
    }
}
