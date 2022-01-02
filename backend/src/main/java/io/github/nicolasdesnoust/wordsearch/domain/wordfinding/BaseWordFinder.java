package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.WordLocation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
abstract class BaseWordFinder implements WordFinder {

    @Override
    public FindWordsResult findWords(Grid grid, List<String> words) {
        List<WordLocation> wordLocations = new ArrayList<>();
        Iterator<String> iterator = grid.getLineIterator(getDirection());
        int lineIndex = 0;

        while (iterator.hasNext()) {
            String line = iterator.next();

            for (String word : words) {
                if (line.contains(word)) {
                    int wordIndex = line.indexOf(word);
                    int x = calculateX(lineIndex, wordIndex, grid.getWidth(), grid.getHeight());
                    int y = calculateY(lineIndex, wordIndex, grid.getWidth(), grid.getHeight());

                    WordLocation wordLocation = new WordLocation(word, x, y, getDirection());
                    log.info("Word " + word + " is at " + wordLocation);
                    wordLocations.add(wordLocation);
                }
            }
            lineIndex++;
        }

        return new FindWordsResult(wordLocations);
    }

    /**
     * @return the direction this WordFinder is responsible for.
     */
    protected abstract Direction getDirection();

    /**
     * Calculate the X coordinate of a word.
     *
     * The calculations are based on where the word
     * was found in a line sequence and grid dimensions.
     *
     * @return the X coordinate of a word.
     */
    protected abstract int calculateX(int lineIndex, int wordIndex, int width, int height);

    /**
     * Calculate the Y coordinate of a word.
     *
     * The calculations are based on where the word
     * was found in a line sequence and grid dimensions.
     *
     * @return the Y coordinate of a word.
     */
    protected abstract int calculateY(int lineIndex, int wordIndex, int width, int height);

}
