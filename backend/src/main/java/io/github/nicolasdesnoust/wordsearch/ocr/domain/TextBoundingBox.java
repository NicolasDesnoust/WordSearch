package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class TextBoundingBox {
    
    int x;
    int y;
    
    int height;
    int width;

}
