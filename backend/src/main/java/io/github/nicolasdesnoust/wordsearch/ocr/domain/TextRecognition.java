package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import java.io.File;

public interface TextRecognition {

    /**
     * Recognizes text in a given image, but ignores any text that is not inside the
     * given bounding box.
     */
    String recognizeTextInside(TextBoundingBox textBoundingBox, File picture);

}
