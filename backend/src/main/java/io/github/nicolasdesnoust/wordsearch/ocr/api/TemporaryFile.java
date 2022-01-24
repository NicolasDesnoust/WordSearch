package io.github.nicolasdesnoust.wordsearch.ocr.api;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import io.github.nicolasdesnoust.wordsearch.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequestScope
public class TemporaryFile {

    private final String temporaryDirectory;

    private File file;

    TemporaryFile(@Value("${word-search.temporary-directory-path}") String temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    public TemporaryFile write(MultipartFile multipartFile) {
        try {
            String fileExtension = FileUtil.getExtension(multipartFile.getOriginalFilename())
                    .orElse("tmp");

            Path temporaryDirectoryPath = Paths.get(temporaryDirectory);
            createTemporaryFile(fileExtension, temporaryDirectoryPath);
            log.debug("Temporary file '{}' created.", this.file);

            multipartFile.transferTo(this.file);

            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void createTemporaryFile(String fileExtension, Path temporaryFolderPath) throws IOException {
        String prefix = "tesseract-";
        String suffix = "." + fileExtension;

        this.file = Files.createTempFile(temporaryFolderPath, prefix, suffix).toFile();
    }

    public File asFile() {
        return this.file;
    }

    @PreDestroy
    void cleanup() throws IOException {
        String path = this.file.getPath();
        Files.delete(this.file.toPath());
        log.debug("Temporary file '{}' deleted.", path);
    }

}