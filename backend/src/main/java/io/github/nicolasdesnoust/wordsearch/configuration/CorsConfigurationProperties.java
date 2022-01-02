package io.github.nicolasdesnoust.wordsearch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Value;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "word-search.cors")
public class CorsConfigurationProperties {

	String allowedOrigins;
	String allowedHeaders;
	String allowedMethods;

}