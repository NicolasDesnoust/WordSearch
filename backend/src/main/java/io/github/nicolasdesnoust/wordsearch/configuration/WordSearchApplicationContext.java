package io.github.nicolasdesnoust.wordsearch.configuration;

import io.github.nicolasdesnoust.wordsearch.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.domain.OCRService;
import io.github.nicolasdesnoust.wordsearch.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.domain.NaiveWordFinder;
import io.github.nicolasdesnoust.wordsearch.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.usecases.ConvertsWordsPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordSearchApplicationContext {

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
    public SolveWordSearchUseCase solveWordSearchUseCase(
            @Autowired GridFactory gridFactory,
            @Autowired WordsFactory wordsFactory,
            @Autowired NaiveWordFinder wordFindingFacade
    ) {
        return new SolveWordSearchUseCase(gridFactory, wordsFactory, wordFindingFacade);
    }

    @Bean
    public OCRService ocrService() {
        return new OCRService();
    }

    @Bean
    public GridFactory gridFactory() {
        return new GridFactory();
    }

    @Bean
    public WordsFactory wordsFactory() {
        return new WordsFactory();
    }

    @Bean
    public NaiveWordFinder wordFinder() {
        return new NaiveWordFinder();
    }

}
