package io.github.nicolasdesnoust.wordsearch.solver.domain;

import lombok.Value;

@Value
public class WordLocation implements Comparable<WordLocation> {
    String word;
    Coordinates coordinates;
    Direction direction;

    @Override
    public int compareTo(WordLocation other) {
        int wordComparisonResult = word.compareTo(other.word);

        if(wordComparisonResult == 0) {
            return direction.compareTo(other.direction);
        }

        return wordComparisonResult;
    }
}