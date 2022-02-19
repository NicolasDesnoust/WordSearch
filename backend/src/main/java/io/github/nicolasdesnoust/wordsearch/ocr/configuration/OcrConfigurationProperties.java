package io.github.nicolasdesnoust.wordsearch.ocr.configuration;

import javax.validation.constraints.Min;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@ConstructorBinding
@ConfigurationProperties(prefix = "word-search.ocr")
public class OcrConfigurationProperties implements OcrConfiguration {

	@Min(50)
	long maximumDuration;

	String dataPath;

	@Builder.Default
	String language = "FRA";

}