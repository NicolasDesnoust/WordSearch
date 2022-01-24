package io.github.nicolasdesnoust.wordsearch.solver.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtil {

    public String getColumn(char[][] matrix, int columnIndex, char defaultCharacter) {
        StringBuilder sb = new StringBuilder();
        
        for(char[] row : matrix) {
            if(columnIndex <= row.length) {
                sb.append(row[columnIndex]);                
            } else {
                sb.append(defaultCharacter);
            }
        }
        
        return sb.toString();
    }

}
