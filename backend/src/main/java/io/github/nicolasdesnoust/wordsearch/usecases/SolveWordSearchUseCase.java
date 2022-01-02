package io.github.nicolasdesnoust.wordsearch.usecases;

import java.util.List;
import java.util.stream.Collectors;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.domain.WordLocation;
import io.github.nicolasdesnoust.wordsearch.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.domain.wordfinding.FindWordsResult;
import io.github.nicolasdesnoust.wordsearch.domain.wordfinding.WordFindingFacade;
import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase.SolveWordSearchResponse.WordLocationDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class SolveWordSearchUseCase {

    private final GridFactory gridFactory;
    private final WordsFactory wordsFactory;
    private final WordFindingFacade wordFinding;

    public SolveWordSearchResponse solveWordSearch(SolveWordSearchRequest request) {
        Grid grid = gridFactory.createFrom(request.getGrid());
        List<String> words = wordsFactory.createFrom(request.getWords());

        FindWordsResult findWordsResult = wordFinding.findWords(grid, words);

        findWordsResult.getWordLocations().forEach(grid::markLettersAsUsed);

        return SolveWordSearchResponse.builder()
                .withInputGrid(grid.getLetters())
                .withWordLocations(toWordLocationDtoList(findWordsResult.getWordLocations()))
                .withUnusedLetters(grid.getUnusedLetters())
                .build();
    }

    private List<WordLocationDto> toWordLocationDtoList(List<WordLocation> wordLocations) {
        return wordLocations.stream()
                .map(WordLocationDto::from)
                .collect(Collectors.toList());
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class SolveWordSearchRequest {
        String words;
        String grid;
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class SolveWordSearchResponse {
        char[][] inputGrid;
        List<WordLocationDto> wordLocations;
        List<Character> unusedLetters;

        @Value
        @Builder(setterPrefix = "with")
        public static class WordLocationDto {
            String word;
            int x;
            int y;
            String direction;

            private static WordLocationDto from(WordLocation wordLocation) {
                return WordLocationDto.builder()
                        .withWord(wordLocation.getWord())
                        .withX(wordLocation.getX())
                        .withY(wordLocation.getY())
                        .withDirection(wordLocation.getDirection().toString())
                        .build();
            }
        }
    }

}
