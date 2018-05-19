import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;


class MathOperationsTest {
    @DisplayName("Parametrised factorial test")
    @ParameterizedTest(name = "run #{index} with [{arguments}]")
    @CsvSource({"24, 4","120, 5","1, 0","0,-1"})
    void factorialTest(int expected, int actual){
        assertEquals(expected, MathOperations.factorial(actual));
    }

}