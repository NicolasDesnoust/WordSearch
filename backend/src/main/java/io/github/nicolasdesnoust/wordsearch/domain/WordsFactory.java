package io.github.nicolasdesnoust.wordsearch.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordsFactory {

    public List<String> createFrom(String wordsAsString) {
        List<String> words = new ArrayList<>();

        try(Scanner scanner = new Scanner(wordsAsString)) {
            while (scanner.hasNext()) {
                words.add(scanner.next().trim());
            }
        }

        return words;
    }

}
