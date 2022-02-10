package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import java.io.File;
import java.util.List;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;

public interface TextDetection {

    /**
     * Detects all text areas inside a given image and returns their bounding boxes.
     */
    List<TextBoundingBox> detectBoundingBoxes(File picture, DetectionMode detectionMode);

}
