package com.geekbrains.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordsCounter {

    private String[] words;
    private final Map<String, Integer> wordsMap;

    public WordsCounter(String[] words) {
        this.words = words;
        wordsMap = Arrays.stream(words)
                .collect(Collectors.toMap(
                        Function.identity(),
                        v -> 1,
                        Integer::sum
                ));
    }

    // должен возвращать количество слов
    public int count(String word) {
        return Optional.of(wordsMap.get(word))
                .orElse(0);
    }

    // должен возвращать топ 3 слов из словаря
    // по убыванию количества повторений
    public List<String> top3() {
        return wordsMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
