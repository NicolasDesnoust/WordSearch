package io.github.nicolasdesnoust.wordsearch.solver.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class WordsFactory {

    public List<String> createFrom(String rawWords) {
        rawWords = rawWords.toUpperCase(Locale.FRENCH);
        rawWords = StringUtils.stripAccents(rawWords);
        List<String> words = extractWords(rawWords);
        words = filterDuplicates(words);
        return words;
    }

    private List<String> extractWords(String rawWords) {
        List<String> words = new ArrayList<>();

        try(Scanner scanner = new Scanner(rawWords)) {
            while (scanner.hasNext()) {
                words.add(scanner.next().trim());
            }
        }
        return words;
    }

    private List<String> filterDuplicates(List<String> rawWords) {
        return new ArrayList<>(new LinkedHashSet<>(rawWords));
    }

}
