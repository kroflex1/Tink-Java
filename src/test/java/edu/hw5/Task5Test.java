package edu.hw5;

import edu.hw5.Task3.DateManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task5Test {
    static Arguments[] carLicensePlates() {
        return new Arguments[] {
            Arguments.of("А123ВЕ777", true),
            Arguments.of("О777ОО177", true),
            Arguments.of("123АВЕ777", false),
            Arguments.of("А123ВГA123", false),
            Arguments.of("А123ВЕ7777", false),
            Arguments.of("077700177", false),
            Arguments.of("AB123BE777", false),
            Arguments.of("A12BE777", false),
            Arguments.of("aaaaА123ВЕ777aaa", false),
            Arguments.of("W123ВA777aaa", false),
            Arguments.of("A123UA777aaa", false)
        };
    }

    @ParameterizedTest
    @MethodSource("carLicensePlates")
    void testParseDate(String carLicensePlate, boolean except) {
        assertEquals(except, Task5.checkCarLicensePlate(carLicensePlate));
    }
}
