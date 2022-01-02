package io.github.nicolasdesnoust.wordsearch.domain.wordfinding;

import java.util.List;

import io.github.nicolasdesnoust.wordsearch.domain.WordLocation;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class FindWordsResult {
    List<WordLocation> wordLocations;
}