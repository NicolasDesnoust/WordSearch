package io.github.nicolasdesnoust.wordsearch.ocr.domain.impl;

import static org.bytedeco.leptonica.global.lept.L_CLONE;
import static org.bytedeco.leptonica.global.lept.boxaGetBox;
import static org.bytedeco.leptonica.global.lept.pixDestroy;

import java.io.File;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bytedeco.leptonica.BOX;
import org.bytedeco.leptonica.BOXA;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.PIXA;
import org.bytedeco.tesseract.global.tesseract;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition.OcrException;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextDetection;

public class TesseractTextDetection extends TesseractClient implements TextDetection {

    public TesseractTextDetection(OcrConfiguration configuration) {
        super(configuration);
    }

    @Override
    public List<TextBoundingBox> detectBoundingBoxes(File picture, DetectionMode detectionMode) {

        PIX pictureReference = null;
        List<TextBoundingBox> boundingBoxes = Collections.emptyList();

        try {

            pictureReference = providePictureToTesseract(picture);

            boundingBoxes = detectBoundingBoxes(detectionMode);

        } catch (OcrException exception) {
            throw exception;
        } catch (Exception exception) {
            handleTesseractExceptions(exception, picture);
        } finally {
            if (pictureReference != null) {
                pixDestroy(pictureReference);
            }
        }

        return boundingBoxes;
    }

    private List<TextBoundingBox> detectBoundingBoxes(DetectionMode detectionMode) {
        int tesseractDetectionMode = detectionMode == DetectionMode.WORD ? tesseract.RIL_WORD : tesseract.RIL_BLOCK;
        BOXA boundingBoxes = tesseractInstance.GetComponentImages(
                tesseractDetectionMode,
                true,
                (PIXA) null,
                (IntBuffer) null);

        int numberOfBoundingBoxes = boundingBoxes.n();
        return IntStream.range(0, numberOfBoundingBoxes)
                .mapToObj((int currentboundingBoxIndex) -> {
                    BOX currentBoundingBox = boxaGetBox(boundingBoxes, currentboundingBoxIndex, L_CLONE);

                    return TextBoundingBox.builder()
                            .withX(currentBoundingBox.x())
                            .withY(currentBoundingBox.y())
                            .withWidth(currentBoundingBox.w())
                            .withHeight(currentBoundingBox.h())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
