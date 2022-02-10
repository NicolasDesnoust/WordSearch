package io.github.nicolasdesnoust.wordsearch.ocr.domain.util;

public interface StopWatch {

    void start(String taskName);

    void stop();

    long getTotalTimeMillis();
}
