package io.github.nicolasdesnoust.wordsearch.solver.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordsFactory {

    public List<String> createFrom(String rawWords) {
        List<String> words = new ArrayList<>();

        try(Scanner scanner = new Scanner(rawWords)) {
            while (scanner.hasNext()) {
                words.add(scanner.next().trim());
            }
        }

        return words;
    }

}
