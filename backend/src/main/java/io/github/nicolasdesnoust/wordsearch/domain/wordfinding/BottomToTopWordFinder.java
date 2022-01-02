package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class BottomToTopWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.BOTTOM_TO_TOP;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        return lineIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        return height - 1 - wordIndex;
    }

}
