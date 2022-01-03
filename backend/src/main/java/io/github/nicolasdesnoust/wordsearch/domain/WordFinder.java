package io.github.nicolasdesnoust.wordsearch.domain;

import lombok.Value;

import java.util.List;

public interface WordFinder {

    FindWordsResult findWords(Grid grid, List<String> words);

    @Value
    class FindWordsResult {
        List<WordLocation> wordLocations;
    }

}
