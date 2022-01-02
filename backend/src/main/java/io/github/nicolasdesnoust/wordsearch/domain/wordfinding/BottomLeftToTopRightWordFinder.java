package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class BottomLeftToTopRightWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.BOTTOM_LEFT_TO_TOP_RIGHT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < height ? 0 : lineIndex - height + 1;
        return temp + wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < height ? lineIndex : height - 1;
        return temp - wordIndex;
    }

}
