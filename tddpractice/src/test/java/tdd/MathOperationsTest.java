package tdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MathOperationsTest {
    @DisplayName("Parametrised factorial test")
    @ParameterizedTest(name = "run #{index} with [{arguments}]")
    @CsvSource({"24, 4", "120, 5", "1, 0", "0,-1"})
    void factorialTest(int expected, int actual) {
        assertEquals(expected, MathOperations.factorial(actual));
    }


    @Disabled
    @Test
    @DisplayName("Parametrised multiply test")
    void multiply() {
        assertEquals(45, MathOperations.multiply(3, 15));
    }

    @Test
    @DisplayName("SImple Arithmetic exception test")
    void devide() {
        assertThrows(ArithmeticException.class,
                () -> MathOperations.devide((int) (Math.random() * 10 + 1), 0));
    }
}