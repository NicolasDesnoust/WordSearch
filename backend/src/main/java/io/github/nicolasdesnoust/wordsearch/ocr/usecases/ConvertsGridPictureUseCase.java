package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import java.io.File;

import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OCRService;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ConvertsGridPictureUseCase {

    private final OCRService ocr;
    private final GridFactory gridFactory;

    @LogUseCaseExecution
    public ConvertsGridPictureResponse convertsGridPicture(ConvertsGridPictureRequest request) {
        String convertedPicture = ocr.convertsPicture(request.getGridPicture());
        char[][] grid = gridFactory.createFrom(convertedPicture).getLetters();
        
        return new ConvertsGridPictureResponse(grid);
    }

    @Value
    public static class ConvertsGridPictureRequest {
        File gridPicture;
    }

    @Value
    public static class ConvertsGridPictureResponse {
        char[][] grid;
    }

}
