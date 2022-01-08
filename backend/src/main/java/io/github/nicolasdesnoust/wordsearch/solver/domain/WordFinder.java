package io.github.nicolasdesnoust.wordsearch.solver.domain;

import java.util.List;

public interface WordFinder {

    List<WordLocation> findWords(Grid grid, List<String> words);

}
