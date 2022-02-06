package io.github.nicolasdesnoust.wordsearch.solver.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GridFactory {

    public Grid createFrom(String rawGrid) {
        List<String> rows = toRows(rawGrid);

        int gridHeight = rows.size();
        int gridWidth = findGridWidth(rows);

        rows = fillHoles(rows, gridWidth);

        char[][] gridAsCharMatrix = toCharMatrix(rows, gridHeight, gridWidth);

        return new Grid(gridAsCharMatrix);
    }

    private List<String> toRows(String rawGrid) {
        List<String> rows = new ArrayList<>();

        try (Scanner scanner = new Scanner(rawGrid)) {
            while (scanner.hasNextLine()) {
                rows.add(scanner.nextLine().trim());
            }
        }

        return rows;
    }

    private int findGridWidth(List<String> rows) {
        return rows.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private List<String> fillHoles(List<String> rows, int gridWidth) {
        return rows.stream()
                .map(row -> StringUtils.rightPad(row, gridWidth))
                .collect(Collectors.toList());
    }

    private char[][] toCharMatrix(List<String> rows, int gridHeight, int gridWidth) {
        char[][] gridAsCharMatrix = new char[gridHeight][gridWidth];

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            gridAsCharMatrix[rowIndex] = rows.get(rowIndex).toCharArray();
        }

        return gridAsCharMatrix;
    }
}
