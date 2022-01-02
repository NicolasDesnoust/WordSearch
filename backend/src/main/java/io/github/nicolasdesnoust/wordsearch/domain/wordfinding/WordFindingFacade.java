package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.WordLocation;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WordFindingFacade {

    /**
     * List of all services responsible for finding words in a grid.
     * There is one WordFinder per direction.
     */
    private final List<WordFinder> wordFinders;

    /**
     * Find all words in a given grid.
     *
     * @return all found words, sorted alphabetically.
     */
    public FindWordsResult findWords(Grid grid, List<String> words) {
        var wordLocations = wordFinders.stream()
                .map(wordFinder -> wordFinder.findWords(grid, words))
                .map(FindWordsResult::getWordLocations)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(WordLocation::getWord))
                .collect(Collectors.toList());

        return new FindWordsResult(wordLocations);
    }
}
