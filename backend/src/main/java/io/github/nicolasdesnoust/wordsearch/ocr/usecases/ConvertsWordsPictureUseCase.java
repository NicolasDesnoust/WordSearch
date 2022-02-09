package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import java.io.File;
import java.util.List;

import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.core.usecases.ValidateRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrResult;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ConvertsWordsPictureUseCase {

    private final OpticalCharacterRecognition ocr;
    private final WordsFactory wordsFactory;

    @LogUseCaseExecution
    public ConvertsWordsPictureResponse convertsWordsPicture(@ValidateRequest ConvertsWordsPictureRequest request) {
        OcrOptions options = OcrOptions.builder()
                .withDetectionMode(DetectionMode.WORD)
                .build();
        OcrResult ocrResult = ocr.convertsPicture(request.getWordsPicture(), options);
        List<String> words = wordsFactory.createFrom(ocrResult.getRecognizedText());

        return new ConvertsWordsPictureResponse(words, ocrResult.isComplete());
    }

    @Value
    public static class ConvertsWordsPictureRequest {
        File wordsPicture;
    }

    @Value
    public static class ConvertsWordsPictureResponse {
        List<String> words;
        boolean isComplete;
    }
}