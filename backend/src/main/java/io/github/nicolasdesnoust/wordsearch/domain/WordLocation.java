package io.github.nicolasdesnoust.wordsearch.domain;

import lombok.Value;

@Value
public class WordLocation {
    String word;
    Coordinates coordinates;
    Direction direction;
}