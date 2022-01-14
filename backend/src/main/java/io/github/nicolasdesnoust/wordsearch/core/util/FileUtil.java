package io.github.nicolasdesnoust.wordsearch.core.util;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {

    public Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
