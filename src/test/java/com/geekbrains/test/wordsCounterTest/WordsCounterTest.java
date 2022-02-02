package com.geekbrains.test.wordsCounterTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.geekbrains.test.AbstractTest;
import com.geekbrains.test.WordsCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordsCounterTest extends AbstractTest {

    @Test
    void count() throws IOException {
        String string = getResourceAsString("data.txt");
        WordsCounter counter = new WordsCounter(string.split(" +"));
        int actual = counter.count("a");
        int expected = 5;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void top3() throws IOException {
        String string = getResourceAsString("data.txt");
        WordsCounter counter = new WordsCounter(string.split(" +"));

        List<String> actual = counter.top3();
        List<String> expected = Arrays.asList(getResourceAsString("expected.txt").split(" +"));

        Assertions.assertEquals(expected, actual);
    }
}