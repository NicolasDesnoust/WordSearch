package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import java.io.File;
import java.util.List;

import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrService;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ConvertsWordsPictureUseCase {

    private final OcrService ocr;
    private final WordsFactory wordsFactory;

    @LogUseCaseExecution
    public ConvertsWordsPictureResponse convertsWordsPicture(ConvertsWordsPictureRequest request) {
        String convertedPicture = ocr.convertsPicture(request.getWordsPicture());
        List<String> words = wordsFactory.createFrom(convertedPicture);

        return new ConvertsWordsPictureResponse(words);
    }

    @Value
    public static class ConvertsWordsPictureRequest {
        File wordsPicture;
    }

    @Value
    public static class ConvertsWordsPictureResponse {
        List<String> words;
    }
}