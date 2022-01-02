package io.github.nicolasdesnoust.wordsearch.api;

import java.io.File;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.nicolasdesnoust.wordsearch.usecases.ConvertsGridPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureRequest;
import io.github.nicolasdesnoust.wordsearch.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/grids")
public class ConvertsGridPictureController {

    private final ConvertsGridPictureUseCase useCase;
    private final TemporaryFile temporaryFile;

    @PostMapping(path = "/_converts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConvertsGridPictureResponse> convertsGridPicture(@RequestParam MultipartFile gridPicture) {
        File file = this.temporaryFile.write(gridPicture).asFile();
        var response = useCase.convertsGridPicture(new ConvertsGridPictureRequest(file));

        return ResponseEntity.ok(response);
    }

}
