package io.github.nicolasdesnoust.wordsearch.ocr.api;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequestScope
public class TemporaryFile {

    private File file;

    public TemporaryFile write(MultipartFile multipartFile) {
        try {
            String fileExtension =  getExtension(multipartFile.getOriginalFilename())
                    .orElse("tmp");
            this.file = File.createTempFile("tesseract-", "." + fileExtension);
            log.debug("Created {}.", this.file);

            multipartFile.transferTo(this.file);

            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public File asFile() {
        return this.file;
    }

    @PreDestroy
    void cleanup() {
        if (this.file.delete()) {
            log.debug("Deleted {}.", this.file);
        } else {
            log.debug("Failed to delete {}.", this.file);
        }
    }

}