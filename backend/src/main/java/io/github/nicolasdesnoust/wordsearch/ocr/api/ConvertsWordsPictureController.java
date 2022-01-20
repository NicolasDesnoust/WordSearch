package io.github.nicolasdesnoust.wordsearch.ocr.api;

import java.io.File;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase.ConvertsWordsPictureRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase.ConvertsWordsPictureResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/words")
public class ConvertsWordsPictureController implements ConvertsWordsPictureSpecification {

    private final ConvertsWordsPictureUseCase useCase;
    private final TemporaryFile temporaryFile;

    @Override
    @PostMapping(path = "/_converts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConvertsWordsPictureResponse> convertsWordsPicture(@RequestParam MultipartFile wordsPicture) {
        File file = this.temporaryFile.write(wordsPicture).asFile();
        var response = useCase.convertsWordsPicture(new ConvertsWordsPictureRequest(file));
        
        return ResponseEntity.ok(response);
    }

}
