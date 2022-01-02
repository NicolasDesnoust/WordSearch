package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class TopLeftToBottomRightWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.TOP_LEFT_TO_BOTTOM_RIGHT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        int j = lineIndex < width ? width - 1 - lineIndex : 0;
        return j + wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        int i = lineIndex < width ? 0 : lineIndex - width + 1;
        return i + wordIndex;
    }

}
