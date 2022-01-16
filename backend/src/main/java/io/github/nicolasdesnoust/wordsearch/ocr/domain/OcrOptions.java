package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class OcrOptions {

    DetectionMode detectionMode;

    public enum DetectionMode {
        WORD,
        GRID;
    }
}
