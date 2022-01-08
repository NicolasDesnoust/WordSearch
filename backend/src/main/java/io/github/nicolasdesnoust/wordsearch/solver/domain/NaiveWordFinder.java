package io.github.nicolasdesnoust.wordsearch.solver.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class NaiveWordFinder implements WordFinder {

    /**
     * Find all words in a given grid.
     *
     * @return all found words, sorted alphabetically.
     */
    @Override
    public List<WordLocation> findWords(Grid grid, List<String> words) {
        List<WordLocation> wordLocations = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            wordLocations.addAll(findWordsInSpecificDirection(grid, words, direction));
        }

        Collections.sort(wordLocations);

        return wordLocations;
    }

    /**
     * Find words in a grid but only in a specific direction (left to right, top to bottom, ...).
     */
    private List<WordLocation> findWordsInSpecificDirection(Grid grid, List<String> words, Direction direction) {
        List<WordLocation> wordLocations = new ArrayList<>();
        Iterator<GridLine> lineIterator = grid.getLineIteratorByDirection(direction);

        while (lineIterator.hasNext()) {
            GridLine line = lineIterator.next();

            for (String word : words) {
                if (line.contains(word)) {
                    Coordinates wordCoordinates = line.calculateCoordinatesOf(word);
                    wordLocations.add(new WordLocation(word, wordCoordinates, direction));
                }
            }
        }

        return wordLocations;
    }
}
