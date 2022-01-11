package io.github.nicolasdesnoust.wordsearch;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Layers {

    public static final String ABSOLUTE_PATH_OF_BASE_PACKAGE = "io.github.nicolasdesnoust.wordsearch";

    @Getter
    public enum FeatureLayer {

        CORE("core"),
        SOLVER("solver"),
        OCR("ocr");

        private final String name;
        private final String absolutePath;

        FeatureLayer(String packageName) {
            this.name = packageName;
            this.absolutePath = ABSOLUTE_PATH_OF_BASE_PACKAGE + "." + packageName + "..";
        }
    }

    @Getter
    public enum TechnicalLayer {

        API("api"),
        USE_CASES("usecases"),
        DOMAIN("domain"),
        CONFIGURATION("configuration"),
        UTIL("util");

        private final String name;
        private final String absolutePath;

        TechnicalLayer(String packageName) {
            this.name = packageName;
            this.absolutePath = ABSOLUTE_PATH_OF_BASE_PACKAGE + ".*." + packageName + "..";
        }
    }

}
