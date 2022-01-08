package io.github.nicolasdesnoust.wordsearch.ocr.configuration;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OCRService;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrApplicationContext {

    @Bean
    public ConvertsGridPictureUseCase convertsGridPictureUseCase(
            @Autowired OCRService ocrService,
            @Autowired GridFactory gridFactory
    ) {
        return new ConvertsGridPictureUseCase(ocrService, gridFactory);
    }

    @Bean
    public ConvertsWordsPictureUseCase convertsWordsPictureUseCase(
            @Autowired OCRService ocrService,
            @Autowired WordsFactory wordsFactory
    ) {
        return new ConvertsWordsPictureUseCase(ocrService, wordsFactory);
    }

    @Bean
    public OCRService ocrService() {
        return new OCRService();
    }

}
