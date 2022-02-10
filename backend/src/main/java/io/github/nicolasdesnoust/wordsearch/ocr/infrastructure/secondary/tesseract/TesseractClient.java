package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.tesseract;

import static org.bytedeco.leptonica.global.lept.pixRead;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition.OcrException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
abstract class TesseractClient {
    
    private static final String OCR_ERROR = "Failed to do optical character recognition on file '%s'.";
    
    protected final OcrConfiguration configuration;

    protected TessBaseAPI tesseractInstance;

    @PostConstruct
    private void initializeTesseract() {        
        tesseractInstance = new TessBaseAPI();

        String language = configuration.getLanguage();
        String dataPath = configuration.getDataPath();
        
        log.info("Initializing a tesseract instance with dataPath: '{}' and language: '{}'", dataPath, language);
        int returnCode = tesseractInstance.Init(dataPath, language);
        if (returnCode != 0) {
            throw new OcrException("could not initialize tesseractInstance, error code: " + returnCode);
        }

        tesseractInstance.SetVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    
    protected PIX providePictureToTesseract(File picture) {
        PIX pixPicture;

        pixPicture = pixRead(picture.getAbsolutePath());
        tesseractInstance.SetImage(pixPicture);

        return pixPicture;
    }

    protected void handleTesseractExceptions(Exception exception, File file) {
        throw new OcrException(String.format(OCR_ERROR, file.getName()), exception);
    }

    @PreDestroy
    private void closeTesseractAndFreeUpMemory() {
        log.info("Closing a tesseract instance");

        if (tesseractInstance != null) {
            tesseractInstance.End();
            tesseractInstance.close();
        }
    }
}
