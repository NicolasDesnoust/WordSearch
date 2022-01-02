package io.github.nicolasdesnoust.wordsearch.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GridFactory {
    
    public Grid createFrom(String gridAsString) {
        List<String> rows = new ArrayList<>();

        try(Scanner scanner = new Scanner(gridAsString)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    rows.add(line);
                }
            }
        }

        int gridWidth = rows.isEmpty() ? 0 : rows.get(0).length();
        int gridHeight = rows.size();
        char[][] gridAsCharMatrix = new char[gridHeight][gridWidth];

        for(int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            gridAsCharMatrix[rowIndex] = rows.get(rowIndex).toCharArray();
        }
        
        return new Grid(gridAsCharMatrix);
    }

}
