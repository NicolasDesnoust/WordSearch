package io.github.nicolasdesnoust.wordsearch.domain;

import lombok.Value;

@Value
public class WordLocation {
    String word;
    int x;
    int y;
    Direction direction;
}