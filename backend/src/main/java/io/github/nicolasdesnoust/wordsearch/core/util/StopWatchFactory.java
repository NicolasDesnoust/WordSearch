package io.github.nicolasdesnoust.wordsearch.core.util;

public class StopWatchFactory {

    public StopWatch createStopWatch() {
        return new SpringStopWatch();
    }

}
