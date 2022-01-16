package io.github.nicolasdesnoust.wordsearch.core.configuration;

import io.github.nicolasdesnoust.wordsearch.core.util.StopWatchFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreApplicationContext {

    @Bean
    public StopWatchFactory stopWatchFactory() {
        return new StopWatchFactory();
    }

}
