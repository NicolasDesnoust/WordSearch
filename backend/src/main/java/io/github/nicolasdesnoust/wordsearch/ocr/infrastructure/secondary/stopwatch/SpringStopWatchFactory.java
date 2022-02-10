package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.stopwatch;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.util.StopWatch;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.util.StopWatchFactory;

public class SpringStopWatchFactory implements StopWatchFactory {

    public StopWatch createStopWatch() {
        return new SpringStopWatch();
    }

}
