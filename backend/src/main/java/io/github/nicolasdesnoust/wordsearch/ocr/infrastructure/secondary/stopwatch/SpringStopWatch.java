package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.stopwatch;

import io.github.nicolasdesnoust.wordsearch.ocr.domain.util.StopWatch;

public class SpringStopWatch implements StopWatch {

    private final org.springframework.util.StopWatch delegate
            = new org.springframework.util.StopWatch();

    @Override
    public void start(String taskName) {
        delegate.start(taskName);
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public long getTotalTimeMillis() {
        return delegate.getTotalTimeMillis();
    }

}
