package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import io.github.nicolasdesnoust.wordsearch.core.domain.UserFriendlyErrorMessage;
import io.github.nicolasdesnoust.wordsearch.core.domain.WordSearchException;

import java.io.File;

public interface OpticalCharacterRecognition {

    /**
     * Extracts text from a given picture.
     */
    OcrResult convertsPicture(File picture, OcrOptions options);

    class OcrException extends WordSearchException {

        public OcrException(String message) {
            super(message, OcrErrorMessage.OPTICAL_CHARACTER_RECOGNITION_ERROR);
        }

        public OcrException(String message, UserFriendlyErrorMessage userFriendlyMessage) {
            super(message, userFriendlyMessage);
        }

        public OcrException(String message, Throwable cause) {
            super(message, OcrErrorMessage.OPTICAL_CHARACTER_RECOGNITION_ERROR, cause);
        }
    }
}

