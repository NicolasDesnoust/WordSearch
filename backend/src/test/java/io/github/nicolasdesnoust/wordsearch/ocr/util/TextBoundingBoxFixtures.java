package io.github.nicolasdesnoust.wordsearch.ocr.util;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import lombok.experimental.UtilityClass;

import java.util.List;

import static java.util.Collections.singletonList;

@UtilityClass
public class TextBoundingBoxFixtures {

    public List<TextBoundingBox> oneTextBoundingBox() {
        return singletonList(
                TextBoundingBox.builder()
                        .withX(0).withY(0)
                        .withWidth(100).withHeight(100)
                        .build()
        );
    }

    public List<TextBoundingBox> someTextBoundingBoxes() {
        return List.of(
                TextBoundingBox.builder()
                        .withX(0).withY(0)
                        .withWidth(100).withHeight(100)
                        .build(),
                TextBoundingBox.builder()
                        .withX(100).withY(0)
                        .withWidth(100).withHeight(100)
                        .build(),
                TextBoundingBox.builder()
                        .withX(200).withY(0)
                        .withWidth(100).withHeight(100)
                        .build()
        );
    }

}
