package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

import java.io.File;
import java.util.Set;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import io.github.nicolasdesnoust.wordsearch.core.util.FileUtil;

public class TesseractOcrService implements OcrService {

    private static final String OCR_ERROR = "Failed to do optical character recognition on file '%s'.";
    private static final String UNSUPPORTED_FORMAT_ERROR = "The image format '%s' is invalid or unsupported.";

    @Override
    public String convertsPicture(File file) {

        validateFileExtension(file);

        TessBaseAPI tesseract = null;
        String language = "FRA";
        PIX pictureReference = null;
        String result = "";

        /*
         * Tesseract instance is not used in a try-with-resources block because it
         * does not seem to implement Autocloseable reliably.
         */
        try {

            tesseract = initializeTesseract(language);
            configureTesseract(tesseract);
            pictureReference = providePictureToTesseract(file, tesseract);

            result = doOpticalCharacterRecognition(tesseract);

        } catch (OcrException exception) {
            throw exception;
        } catch (Exception exception) {
            handleTesseractExceptions(exception, file);
        } finally {
            closeResourcesAndFreeUpMemory(tesseract, pictureReference);
        }

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

    private void handleTesseractExceptions(Exception exception, File file) {
        throw new OcrException(String.format(OCR_ERROR, file.getName()), exception);
    }

    private TessBaseAPI initializeTesseract(String language) {
        TessBaseAPI tesseract = new TessBaseAPI();

        String datapath = null; // Datapath is instead passed as an environment variable
        int returnCode = tesseract.Init(datapath, language);
        if (returnCode != 0) {
            throw new OcrException("could not initialize tesseract, error code: " + returnCode);
        }

        return tesseract;
    }

    private void configureTesseract(TessBaseAPI tesseract) {
        tesseract.SetVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    private PIX providePictureToTesseract(File picture, TessBaseAPI tesseract) {
        PIX pixPicture;

        pixPicture = pixRead(picture.getAbsolutePath());
        tesseract.SetImage(pixPicture);

        return pixPicture;
    }

    private String doOpticalCharacterRecognition(TessBaseAPI tesseract) {
        BytePointer ocrResult = tesseract.GetUTF8Text();
        String ocrResultAsString = ocrResult.getString()
                .trim()
                .replaceAll("(?m)^[ \t]*\r?\n", "");

        ocrResult.deallocate();

        return ocrResultAsString;

    }

    private void closeResourcesAndFreeUpMemory(TessBaseAPI tesseract, PIX pictureReference) {
        if (pictureReference != null) {
            pixDestroy(pictureReference);
        }

        if (tesseract != null) {
            tesseract.End();
            tesseract.close();
        }
    }

    public static class UnsupportedFormatException extends OcrException {

        public UnsupportedFormatException(String message) {
            super(message);
        }
    }

}
