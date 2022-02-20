package io.github.nicolasdesnoust.wordsearch.ocr.configuration;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Min;

@Value
@ConfigurationProperties(prefix = "word-search.ocr")
@Builder(setterPrefix = "with")
@AllArgsConstructor
@ConstructorBinding
public class OcrConfigurationProperties implements OcrConfiguration {

    @Min(50)
    long maximumDuration;

    String dataPath;

    String language;

}