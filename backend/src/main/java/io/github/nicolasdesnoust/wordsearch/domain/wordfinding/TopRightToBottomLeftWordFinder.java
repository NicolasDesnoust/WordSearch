package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class TopRightToBottomLeftWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.TOP_RIGHT_TO_BOTTOM_LEFT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < width ? lineIndex : width - 1;
        return temp - wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < width ? 0 : lineIndex - width + 1;
        return temp + wordIndex;
    }

}
