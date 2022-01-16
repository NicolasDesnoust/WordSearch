package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class OcrResult {

    /**
     * The text that could be extracted from the picture within the time limit.
     */
    String recognizedText;

    /**
     * Indicates whether the job could be completed within the time limit.
     */
    boolean isComplete;
    
}
