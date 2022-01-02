package io.github.nicolasdesnoust.wordsearch.api;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequestScope
public class TemporaryFile {

    private final File file;

    public TemporaryFile() {
        try {
            this.file = File.createTempFile("tesseract-", ".tmp");
            log.debug("Created {}.", this.file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public TemporaryFile write(MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(this.file);
            return this;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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