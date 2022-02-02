package com.geekbrains.test;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        calculator = new Calculator();
    }

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(4, 4, 8)
        );
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Тест суммы двух чисел")
    void sum() {
        int actual = calculator.sum(1, 2);
        int expected = 3;
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{index}) {0} + {1} = {2}")
    @MethodSource("data")
    @DisplayName("Тесты суммы двух чисел")
    void sumParams(int x, int y, int expected) {
        Assertions.assertEquals(expected, calculator.sum(x, y));
    }
}