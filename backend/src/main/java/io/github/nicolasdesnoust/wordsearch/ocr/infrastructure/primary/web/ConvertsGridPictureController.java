package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.primary.web;

import java.io.File;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/grids")
class ConvertsGridPictureController implements ConvertsGridPictureSpecification {

    private final ConvertsGridPictureUseCase useCase;
    private final TemporaryFile temporaryFile;

    @Override
    @PostMapping(path = "/_converts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConvertsGridPictureResponse> convertsGridPicture(@RequestParam MultipartFile gridPicture) {
        File file = this.temporaryFile.write(gridPicture).asFile();
        var response = useCase.convertsGridPicture(new ConvertsGridPictureRequest(file));

        return ResponseEntity.ok(response);
    }

}
