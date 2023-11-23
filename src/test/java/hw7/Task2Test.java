package hw7;

import edu.hw7.Task2;
import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task2Test {

    static Stream<Arguments> factorials() {
        return Stream.of(
            arguments(1L, BigInteger.valueOf(1)),
            arguments(2L, BigInteger.valueOf(2)),
            arguments(3L, BigInteger.valueOf(6)),
            arguments(4L, BigInteger.valueOf(24)),
            arguments(5L, BigInteger.valueOf(120)),
            arguments(6L, BigInteger.valueOf(720)),
            arguments(7L, BigInteger.valueOf(5040)),
            arguments(8L, BigInteger.valueOf(40320)),
            arguments(9L, BigInteger.valueOf(362880)),
            arguments(10L, BigInteger.valueOf(3628800)),
            arguments(20L, BigInteger.valueOf(2432902008176640000L))
        );
    }

    @ParameterizedTest
    @MethodSource("factorials")
    void testCalculateFactorial(long value, BigInteger exceptFactorial) {
        BigInteger result = Task2.getFactorial(value);
        assertEquals(exceptFactorial, result);
    }
}
