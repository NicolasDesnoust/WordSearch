package io.github.nicolasdesnoust.wordsearch.architecture;

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

        DOMAIN("domain"),
        USE_CASES("usecases"),
        INFRASTRUCTURE("infrastructure"),
        CONFIGURATION("configuration"),
        AOP("aop");

        private final String name;
        private final String absolutePath;

        TechnicalLayer(String relativePath) {
            this.name = relativePath;
            this.absolutePath = ABSOLUTE_PATH_OF_BASE_PACKAGE + ".*." + relativePath + "..";
        }
    }

}
