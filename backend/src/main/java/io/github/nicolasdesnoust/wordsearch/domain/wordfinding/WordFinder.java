package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

import java.util.List;

public interface WordFinder {
    FindWordsResult findWords(Grid grid, List<String> words);
}
