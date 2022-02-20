package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.tesseract;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextDetection;
import io.github.nicolasdesnoust.wordsearch.ocr.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TesseractTextDetectionIT {

    @Autowired
    TextDetection underTest;

    @Test
    void shouldReturnNoBoundingBoxesWhenPictureIsBlank() {
        File aBlankPicture = FileUtil.getFile("/images/blank-picture.jpg");

        var boundingBoxes = underTest.detectBoundingBoxes(aBlankPicture, DetectionMode.WORD);

        assertThat(boundingBoxes).isEmpty();
    }

    @Test
    void shouldReturnOneBoundingBoxInGridDetectionMode() {
        File aValidPicture = FileUtil.getFile("/images/valid-grid-picture.jpg");

        var boundingBoxes = underTest.detectBoundingBoxes(aValidPicture, DetectionMode.GRID);

        assertThat(boundingBoxes).hasSize(1);
    }

    @Test
    void shouldReturnMultipleBoundingBoxesInWordDetectionMode() {
        File wordsPicture = FileUtil.getFile("/images/valid-picture.jpg");

        var boundingBoxes = underTest.detectBoundingBoxes(wordsPicture, DetectionMode.WORD);

        assertThat(boundingBoxes).hasSize(192);
    }
}
