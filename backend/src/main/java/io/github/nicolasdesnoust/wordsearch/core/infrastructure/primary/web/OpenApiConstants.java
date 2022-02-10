package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import lombok.experimental.UtilityClass;

/**
 * Utility constants to share and reuse things related to Open API Documentation.
 * <p>
 * These are constants because Open API Documentation is built mostly using annotations
 * and constants are the only thing we can use inside annotations.
 */
@UtilityClass
public class OpenApiConstants {

    @UtilityClass
    public class Solver {
        public static final String TAG_NAME = "Solveur de mots-mêlés";
        public static final String TAG_DESCRIPTION = "Ce module regroupe tout ce qui concerne la résolution de grilles de mots-mêlés.";
    }

    @UtilityClass
    public class Ocr {
        public static final String TAG_NAME = "OCR (Reconnaissance optique des caractères)";
        public static final String TAG_DESCRIPTION = "Ce module regroupe tout ce qui concerne la reconnaissance optique des caractères.";
    }

}
