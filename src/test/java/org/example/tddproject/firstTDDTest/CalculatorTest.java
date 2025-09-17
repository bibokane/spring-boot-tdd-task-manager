package org.example.tddproject.firstTDDTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    void testDivideTwoNumbers() {
        // arrange
        Calculator calculator = new Calculator();

        // act
        double result = calculator.divide(6, 2);

        // assert
        assertEquals(3.0, result);
    }

    @Test
    void testDivideZero() {
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class, () -> calculator.divide(6, 0));
    }
    @Test
    void testDivideNegativeNumbers() {
        Calculator calculator = new Calculator();
        double result = calculator.divide(-6, -2);
        assertEquals(3.0, result);
        double result2 = calculator.divide(-6, 2);
        assertEquals(-3.0, result2);
        double result3 = calculator.divide(6, -2);
        assertEquals(-3.0, result3);

    }
}
