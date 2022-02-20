package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.tesseract;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import io.github.nicolasdesnoust.wordsearch.ocr.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.function.Predicate;

import static java.util.function.Predicate.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TesseractTextRecognitionIT {

    @Autowired
    TesseractTextRecognition underTest;

    @Test
    void shouldFindTheWordInsideTheBoundingBox() {
        File wordsPicture = FileUtil.getFile("/images/valid-picture.jpg");
        TextBoundingBox boundingBox = TextBoundingBox.builder()
                .withX(480).withY(104)
                .withHeight(28).withWidth(163)
                .build();

        String recognizedText = underTest.recognizeTextInside(boundingBox, wordsPicture);

        assertThat(recognizedText).isEqualTo("GRIFFONNAGE");
    }

    @Test
    void shouldFindAllGridLetters() {
        File gridPicture = FileUtil.getFile("/images/valid-grid-picture.jpg");
        TextBoundingBox fullSizeBoundingBox = TextBoundingBox.builder()
                .withX(0).withY(0)
                .withHeight(384).withWidth(384)
                .build();

        String recognizedText = underTest.recognizeTextInside(fullSizeBoundingBox, gridPicture);

        String expectedGrid = """
                CLASSESMOP
                HTIDMIYUIE
                HEBZBEPTTL
                ESRLMMPAEB
                AAAEODABRI
                RFCHDCHLAX
                RUOHUOKETE
                ARBHLACSOL
                YPPYEINSRF
                ESSTCEJBOS
                """;
        assertThat(recognizedText.lines().filter(not(String::isBlank)))
                .containsExactlyElementsOf(expectedGrid.lines().toList());

    }

}
