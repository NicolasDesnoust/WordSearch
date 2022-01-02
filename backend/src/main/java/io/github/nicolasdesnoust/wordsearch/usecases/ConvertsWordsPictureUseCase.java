package io.github.nicolasdesnoust.wordsearch.usecases;

import java.io.File;
import java.util.List;

import io.github.nicolasdesnoust.wordsearch.domain.OCRService;
import io.github.nicolasdesnoust.wordsearch.domain.WordsFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ConvertsWordsPictureUseCase {

    private final OCRService ocr;
    private final WordsFactory wordsFactory;

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