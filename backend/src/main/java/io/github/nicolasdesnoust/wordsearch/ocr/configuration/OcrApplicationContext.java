package io.github.nicolasdesnoust.wordsearch.ocr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrService;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TesseractOcrService;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;

@Configuration
public class OcrApplicationContext {

    @Bean
    public ConvertsGridPictureUseCase convertsGridPictureUseCase(
            @Autowired OcrService ocrService,
            @Autowired GridFactory gridFactory
    ) {
        return new ConvertsGridPictureUseCase(ocrService, gridFactory);
    }

    @Bean
    public ConvertsWordsPictureUseCase convertsWordsPictureUseCase(
            @Autowired OcrService ocrService,
            @Autowired WordsFactory wordsFactory
    ) {
        return new ConvertsWordsPictureUseCase(ocrService, wordsFactory);
    }

    @Bean
    public TesseractOcrService ocrService() {
        return new TesseractOcrService();
    }

}
