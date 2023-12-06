package edu.hw5;

import edu.hw5.Task3.DateManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    static Arguments[] passwords() {
        List<Arguments> arguments = new ArrayList<>();
        for (String requiredSymbol : List.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "|")) {
            arguments.add(Arguments.of("bla" + requiredSymbol + "bla", true));
        }
        arguments.add(Arguments.of("bla[bla@bla", true));
        arguments.add(Arguments.of("blabla", false));
        arguments.add(Arguments.of("", false));
        return arguments.toArray(new Arguments[0]);
    }

    @ParameterizedTest
    @MethodSource("passwords")
    void testParseDate(String password, boolean except) {

        assertEquals(except, Task4.checkPasswordFormat(password));
    }
}
