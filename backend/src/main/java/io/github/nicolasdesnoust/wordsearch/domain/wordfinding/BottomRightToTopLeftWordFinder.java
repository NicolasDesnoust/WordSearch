package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class BottomRightToTopLeftWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.BOTTOM_RIGHT_TO_TOP_LEFT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < height ? width - 1
                : width - 1 - (lineIndex - height) - 1;
        return temp - wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        int temp = lineIndex < height ? lineIndex : height - 1;
        return temp - wordIndex;
    }

}
