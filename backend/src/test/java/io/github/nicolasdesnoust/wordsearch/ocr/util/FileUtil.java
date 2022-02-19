package io.github.nicolasdesnoust.wordsearch.ocr.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@UtilityClass
public class FileUtil {

    public File getFile(String filePath) {
        URI fileUri;
        try {
            fileUri = FileUtil.class.getResource(filePath).toURI();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
        return Paths.get(fileUri).toFile();
    }

    public boolean exists(String filePath) {
        URL fileUrl = FileUtil.class.getResource(filePath);
        return fileUrl != null;
    }
}
