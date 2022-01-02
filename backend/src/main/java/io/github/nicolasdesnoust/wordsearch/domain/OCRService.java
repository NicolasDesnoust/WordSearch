package io.github.nicolasdesnoust.wordsearch.domain;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;

import java.io.File;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

public class OCRService {
    public String convertsPicture(File file) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with Frnech, without specifying tessdata path
        if (api.Init(".", "ENG") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }
        api.SetVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        api.SetVariable("load_system_dawg", "F");
        api.SetVariable("load_freq_dawg", "F");
        api.SetVariable("load_punc_dawg", "F");
        api.SetVariable("load_number_dawg", "F");
        api.SetVariable("load_unambig_dawg", "F");
        api.SetVariable("load_bigram_dawg", "F");
        api.SetVariable("load_fixed_length_dawgs", "F");
        api.SetVariable("tessedit_write_images", "true");
        // Open input image with leptonica library
        PIX image = pixRead(file.getAbsolutePath());
        api.SetImage(image);

        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();
        // System.out.println("OCR output:\n" + string);

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);

        return string.replaceAll("(?m)^[ \t]*\r?\n", "");
    }
}
