package org.example.productcatalogservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void Test_AddWithTwoIntegers_RunsSuccessfully() {
        //Arrange
        Calculator calc = new Calculator();

        //Act
        int result = calc.add(10, 290);

        //Assert
        assert (result == 300);
    }

    @Test
    void Test_DivideByZero_ThrowsArithmeticException() {
        //Arrange
        Calculator calc = new Calculator();

        //Act and Assert
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }
}