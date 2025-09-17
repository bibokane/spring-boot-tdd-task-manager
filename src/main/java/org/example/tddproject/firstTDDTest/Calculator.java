package org.example.tddproject.firstTDDTest;

public class Calculator {
    public double divide(double a, double b) {
        double result = 0;
        if (b ==0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        else {
            return a/b;
    }

}
}
