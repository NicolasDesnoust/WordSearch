package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import java.io.File;

public interface OpticalCharacterRecognition {

    /**
     * Extracts text from a given picture.
     */
    OcrResult convertsPicture(File picture, OcrOptions options);

    class OcrException extends RuntimeException {

        public OcrException(String message) {
            super(message);
        }

        public OcrException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

