package io.github.nicolasdesnoust.wordsearch.domain;

import io.github.nicolasdesnoust.wordsearch.domain.iterators.BottomLeftToTopRightIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.BottomRightToTopLeftIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.BottomToTopIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.LeftToRightIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.RightToLeftIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.TopLeftToBottomRightIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.TopRightToBottomLeftIterator;
import io.github.nicolasdesnoust.wordsearch.domain.iterators.TopToBottomIterator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class Grid {
    private final char[][] letters;
    private final int[][] lettersUsage;

    public Grid(char[][] letters) {
        this.letters = letters;

        int height = letters.length;
        int width = letters.length > 0 ? letters[0].length : 0;
        this.lettersUsage = new int[height][width];
    }

    public void markLettersAsUsed(WordLocation wordLocation) {
        int wordLength = wordLocation.getWord().length();
        int x = wordLocation.getCoordinates().getX();
        int y = wordLocation.getCoordinates().getY();
        int xMove = wordLocation.getDirection().getXMove();
        int yMove = wordLocation.getDirection().getYMove();

        lettersUsage[y][x]++;
        for (int i = 1; i < wordLength; i++) {
            x += xMove;
            y += yMove;

            lettersUsage[y][x]++;
        }
    }

    public List<Character> getUnusedLetters() {
        List<Character> lettersNotUsed = new ArrayList<>();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (lettersUsage[i][j] == 0) {
                    lettersNotUsed.add(letters[i][j]);
                }
            }
        }

        return lettersNotUsed;
    }

    public Iterator<GridLine> getLineIteratorByDirection(Direction direction) {
        switch (direction) {
            case TOP_TO_BOTTOM:
                return new TopToBottomIterator(this);
            case BOTTOM_LEFT_TO_TOP_RIGHT:
                return new BottomLeftToTopRightIterator(this);
            case BOTTOM_RIGHT_TO_TOP_LEFT:
                return new BottomRightToTopLeftIterator(this);
            case BOTTOM_TO_TOP:
                return new BottomToTopIterator(this);
            case RIGHT_TO_LEFT:
                return new RightToLeftIterator(this);
            case TOP_LEFT_TO_BOTTOM_RIGHT:
                return new TopLeftToBottomRightIterator(this);
            case TOP_RIGHT_TO_BOTTOM_LEFT:
                return new TopRightToBottomLeftIterator(this);
            case LEFT_TO_RIGHT:
            default:
                return new LeftToRightIterator(this);
        }
    }

    public int getWidth() {
        return letters.length > 0 ? letters[0].length : 0;
    }

    public int getHeight() {
        return letters.length;
    }
}
