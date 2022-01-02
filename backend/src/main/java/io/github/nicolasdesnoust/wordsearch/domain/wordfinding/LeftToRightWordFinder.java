package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;

public class LeftToRightWordFinder extends BaseWordFinder {

    @Override
    protected Direction getDirection() {
        return Direction.LEFT_TO_RIGHT;
    }

    @Override
    protected int calculateX(int lineIndex, int wordIndex, int width, int height) {
        return wordIndex;
    }

    @Override
    protected int calculateY(int lineIndex, int wordIndex, int width, int height) {
        return lineIndex;
    }
    
}
