package io.github.nicolasdesnoust.wordsearch.ocr.configuration;

import io.github.nicolasdesnoust.wordsearch.core.util.StopWatchFactory;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextDetection;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.impl.OpticalCharacterRecognitionImpl;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.impl.TesseractTextDetection;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.impl.TesseractTextRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OcrConfigurationProperties.class)
public class OcrApplicationContext {

    @Bean
    public ConvertsGridPictureUseCase convertsGridPictureUseCase(
            @Autowired OpticalCharacterRecognition ocrService,
            @Autowired GridFactory gridFactory
    ) {
        return new ConvertsGridPictureUseCase(ocrService, gridFactory);
    }

    @Bean
    public ConvertsWordsPictureUseCase convertsWordsPictureUseCase(
            @Autowired OpticalCharacterRecognition ocrService,
            @Autowired WordsFactory wordsFactory
    ) {
        return new ConvertsWordsPictureUseCase(ocrService, wordsFactory);
    }

    @Bean
    public OpticalCharacterRecognitionImpl ocrService(
            @Autowired TextDetection textDetection,
            @Autowired TextRecognition textRecognition,
            @Autowired OcrConfiguration configuration,
            @Autowired StopWatchFactory stopWatchFactory
    ) {
        return new OpticalCharacterRecognitionImpl(
                textDetection,
                textRecognition,
                configuration,
                stopWatchFactory
        );
    }

    @Bean
    public TesseractTextDetection textDetection(@Autowired OcrConfiguration configuration) {
        return new TesseractTextDetection(configuration);
    }

    @Bean
    public TesseractTextRecognition textRecognition(@Autowired OcrConfiguration configuration) {
        return new TesseractTextRecognition(configuration);
    }

}
