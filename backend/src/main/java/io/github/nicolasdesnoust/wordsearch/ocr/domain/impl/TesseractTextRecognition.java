package io.github.nicolasdesnoust.wordsearch.ocr.domain.impl;

import static org.bytedeco.leptonica.global.lept.pixDestroy;

import java.io.File;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrConfiguration;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition.OcrException;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextRecognition;

public class TesseractTextRecognition extends TesseractClient implements TextRecognition {

    public TesseractTextRecognition(OcrConfiguration configuration) {
        super(configuration);
    }

    @Override
    public String recognizeTextInside(TextBoundingBox textBoundingBox, File picture) {
        PIX pictureReference = null;
        String result = "";
        
        try {

            pictureReference = providePictureToTesseract(picture);

            result = recognizeTextInside(textBoundingBox);

        } catch (OcrException exception) {
            throw exception;
        } catch (Exception exception) {
            handleTesseractExceptions(exception, picture);
        } finally {
            if (pictureReference != null) {
                pixDestroy(pictureReference);
            }
        }

        return result;
    }

    private String recognizeTextInside(TextBoundingBox textBoundingBox) {
        tesseractInstance.SetRectangle(
            textBoundingBox.getX(), 
            textBoundingBox.getY(), 
            textBoundingBox.getWidth(), 
            textBoundingBox.getHeight()
        );
		
        BytePointer ocrResult = tesseractInstance.GetUTF8Text();
		String recognizedText = ocrResult.getString().trim();
		
        ocrResult.deallocate();
		
		return recognizedText;
    }

}
