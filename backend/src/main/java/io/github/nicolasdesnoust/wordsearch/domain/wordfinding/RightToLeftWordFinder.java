package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class RightToLeftWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.RIGHT_TO_LEFT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        return width - 1 - wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        return lineIndex;
    }

}
