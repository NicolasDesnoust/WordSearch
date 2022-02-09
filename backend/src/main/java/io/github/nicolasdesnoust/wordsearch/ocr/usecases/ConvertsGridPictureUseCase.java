package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import java.io.File;

import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.core.usecases.ValidateRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrResult;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ConvertsGridPictureUseCase {

    private final OpticalCharacterRecognition ocr;
    private final GridFactory gridFactory;

    @LogUseCaseExecution
    public ConvertsGridPictureResponse convertsGridPicture(@ValidateRequest ConvertsGridPictureRequest request) {
        OcrOptions options = OcrOptions.builder()
                .withDetectionMode(DetectionMode.GRID)
                .build();
        OcrResult ocrResult = ocr.convertsPicture(request.getGridPicture(), options);
        
        char[][] grid = gridFactory.createFrom(ocrResult.getRecognizedText())
                .getLetters();

        return new ConvertsGridPictureResponse(grid, ocrResult.isComplete());
    }

    @Value
    public static class ConvertsGridPictureRequest {
        File gridPicture;
    }

    @Value
    public static class ConvertsGridPictureResponse {
        char[][] grid;
        boolean isComplete;
    }

}
