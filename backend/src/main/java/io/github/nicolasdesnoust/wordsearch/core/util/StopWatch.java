package io.github.nicolasdesnoust.wordsearch.core.util;

public interface StopWatch {

    void start(String taskName);

    void stop();

    long getTotalTimeMillis();
}
