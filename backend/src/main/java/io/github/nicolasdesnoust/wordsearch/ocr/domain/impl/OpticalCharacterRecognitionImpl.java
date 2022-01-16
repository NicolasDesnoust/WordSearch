package io.github.nicolasdesnoust.wordsearch.ocr.domain.impl;

import java.io.File;
import java.util.List;
import java.util.Set;

import io.github.nicolasdesnoust.wordsearch.core.util.FileUtil;
import io.github.nicolasdesnoust.wordsearch.core.util.StopWatch;
import io.github.nicolasdesnoust.wordsearch.core.util.StopWatchFactory;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrResult;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextDetection;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextRecognition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OpticalCharacterRecognitionImpl implements OpticalCharacterRecognition {

    private static final String UNSUPPORTED_FORMAT_ERROR = "The image format '%s' is invalid or unsupported.";

    private final TextDetection textDetection;
    private final TextRecognition textRecognition;
    private final OcrConfiguration configuration;
    private final StopWatchFactory stopWatchFactory;

    @Override
    public OcrResult convertsPicture(File picture, OcrOptions options) {

        validateFileExtension(picture);

        StopWatch stopWatch = stopWatchFactory.createStopWatch();
        log.debug("Starting OCR job");

        List<TextBoundingBox> boundingBoxes = textDetection.detectBoundingBoxes(
                picture, options.getDetectionMode());

        OcrResult result = recognizeText(picture, stopWatch, boundingBoxes);

        log.debug("Stopping OCR Job. Elapsed time : {} ms", stopWatch.getTotalTimeMillis());

        return result;
    }

    private void validateFileExtension(File file) {
        Set<String> supportedFileExtensions = getSupportedFileExtensions();

        String fileExtension = FileUtil.getExtension(file.getName()).orElse("<UNKNOWN>");

        if (!supportedFileExtensions.contains(fileExtension)) {
            throw new UnsupportedFormatException(
                    String.format(UNSUPPORTED_FORMAT_ERROR, fileExtension));
        }

    }

    private Set<String> getSupportedFileExtensions() {
        return Set.of("bmp", "png", "jpeg", "jpg");
    }

    private OcrResult recognizeText(File picture, StopWatch stopWatch, List<TextBoundingBox> boundingBoxes) {
        boolean outOfTime = false;
        int boundingBoxIndex = 0;
        StringBuilder recognizedTextBuilder = new StringBuilder();

        while (!outOfTime && boundingBoxIndex < boundingBoxes.size()) {
            stopWatch.start("box-" + boundingBoxIndex);

            TextBoundingBox currentBoundingBox = boundingBoxes.get(boundingBoxIndex);
            String recognizedTextPart = textRecognition.recognizeTextInside(currentBoundingBox, picture);
            recognizedTextBuilder.append(recognizedTextPart)
                .append("\n");

            stopWatch.stop();
            long elapsedTime = stopWatch.getTotalTimeMillis();
            log.debug("{} / {} Item(s) processed", boundingBoxIndex + 1, boundingBoxes.size());

            if (elapsedTime >= configuration.getMaximumDuration()) {
                outOfTime = true;
                log.warn("OCR Job must be stopped : reached maximum duration allowed of {} ms",
                        configuration.getMaximumDuration());
            }

            boundingBoxIndex++;
        }

        return OcrResult.builder()
                .withRecognizedText(recognizedTextBuilder.toString())
                .withIsComplete(!outOfTime)
                .build();
    }

    public static class UnsupportedFormatException extends OcrException {

        public UnsupportedFormatException(String message) {
            super(message);
        }
    }

}
