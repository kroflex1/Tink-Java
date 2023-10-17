package edu.hw2;

import edu.hw2.Task1.Addition;
import edu.hw2.Task1.Constant;
import edu.hw2.Task1.Exponent;
import edu.hw2.Task1.Multiplication;
import edu.hw2.Task1.Negate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {

    private final double DELTA = 1e-15;

    @Test
    @DisplayName("Пример из задания")
    void test1() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));
        assertEquals(37, res.evaluate(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, -1, 0, 0.5, -0.5})
    @DisplayName("Корректная работа Negate с Constant")
    void checkConstant(double value) {
        assertEquals(value, new Constant(value).evaluate(), DELTA);
    }

    @ParameterizedTest
    @CsvSource(value = {"1;1", "1;-1", "-1;-1", "0.5;0.3", "0.5;0.5", "-0.5;0.3"}, delimiter = ';')
    @DisplayName("Корректная работа Addition с Constant")
    void checkAdditionAndConstants(double firstValue, double secondValue) {
        var firstConstant = new Constant(firstValue);
        var secondConstant = new Constant(secondValue);
        assertEquals(firstValue + secondValue, new Addition(firstConstant, secondConstant).evaluate(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, -1, 0, 0.5, -0.5})
    @DisplayName("Корректная работа Negate с Constant")
    void checkNegateAndConstant(double value) {
        var constant = new Constant(value);
        assertEquals(-value, new Negate(constant).evaluate(), DELTA);
    }

    @ParameterizedTest
    @CsvSource(value = {"1;1", "1;-1", "-1;-1", "2;3", "0.5;0.3", "-0.5;0.3", "0.5;0.5", "7.2;4.3"}, delimiter = ';')
    @DisplayName("Корректная работа Multiplication с Constant")
    void checkMultiplicationAndConstant(double firstValue, double secondValue) {
        var firstConstant = new Constant(firstValue);
        var secondConstant = new Constant(secondValue);
        assertEquals(firstValue * secondValue, new Multiplication(firstConstant, secondConstant).evaluate(), DELTA);
    }

    @ParameterizedTest
    @CsvSource(value = {"1;1", "1;-1", "2;3", "2;-3", "4;0.5", "0.5;4", "4;2.5"}, delimiter = ';')
    @DisplayName("Корректная работа Exponent с Constant")
    void checkExponentAndConstant(double firstValue, double secondValue) {
        var firstConstant = new Constant(firstValue);
        var secondConstant = new Constant(secondValue);
        assertEquals(Math.pow(firstValue, secondValue), new Exponent(firstConstant, secondConstant).evaluate(), DELTA);
    }

    @Test
    @DisplayName("Создание Constant с помощью различных Expr")
    void checkCreationOfConstant() {
        var firstConstant = new Constant(2);
        var secondConstant = new Constant(3);
        var sumConstant = new Addition(firstConstant, secondConstant);
        assertEquals(firstConstant.evaluate() + secondConstant.evaluate(), sumConstant.evaluate(), DELTA);
        var negateConstant = new Negate(firstConstant);
        assertEquals(-firstConstant.evaluate(), negateConstant.evaluate(), DELTA);
        var multiplicationConstant = new Multiplication(firstConstant, secondConstant);
        assertEquals(firstConstant.evaluate() * secondConstant.evaluate(), multiplicationConstant.evaluate(), DELTA);
        var exponentConstant = new Exponent(firstConstant, secondConstant);
        assertEquals(Math.pow(firstConstant.evaluate(), secondConstant.evaluate()), exponentConstant.evaluate(), DELTA);
    }

}

