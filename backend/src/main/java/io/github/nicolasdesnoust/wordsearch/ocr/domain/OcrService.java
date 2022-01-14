package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import java.io.File;

public interface OcrService {

    String convertsPicture(File file);

    class OcrException extends RuntimeException {

        public OcrException(String message) {
            super(message);
        }

        public OcrException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

