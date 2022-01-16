package io.github.nicolasdesnoust.wordsearch.core.util;

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
