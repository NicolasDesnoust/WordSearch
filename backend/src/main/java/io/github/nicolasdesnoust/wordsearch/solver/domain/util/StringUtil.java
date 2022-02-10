package io.github.nicolasdesnoust.wordsearch.solver.domain.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String reverse(String stringToReverse) {
        return new StringBuilder(stringToReverse)
                .reverse()
                .toString();
    }

}
