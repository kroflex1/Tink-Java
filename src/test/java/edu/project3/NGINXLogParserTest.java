package edu.project3;

import edu.project3.LogStatitic.LogInf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NGINXLogParserTest {
    static Stream<Arguments> logs() {
        String firstLog =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        OffsetDateTime firstTime = convertToTime("17/May/2015:08:05:32", ZoneOffset.of("+00:00"));
        LogInf firstExcept = new LogInf(
            "93.180.71.3",
            Optional.empty(),
            firstTime,
            "GET /downloads/product_1 HTTP/1.1",
            "product_1",
            304,
            0,
            Optional.empty(),
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        );

        String secondLog =
            "216.46.173.126 - - [26/May/2015:19:05:02 +0200] \"GET /index.html HTTP/1.1\" 404 331 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"";
        OffsetDateTime secondTime = convertToTime("26/May/2015:19:05:02", ZoneOffset.of("+02:00"));
        LogInf secondExcept = new LogInf(
            "216.46.173.126",
            Optional.empty(),
            secondTime,
            "GET /index.html HTTP/1.1",
            "index.html",
            404,
            331,
            Optional.empty(),
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)"
        );

        String thirdLog =
            "78.109.87.141 - Kroflex [26/May/2015:22:05:46 +0245] \"GET /downloads/games/minecraft HTTP/1.1\" 404 336 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"";
        OffsetDateTime thirdTime = convertToTime("26/May/2015:22:05:46", ZoneOffset.of("+02:45"));
        LogInf thirdExcept = new LogInf(
            "78.109.87.141",
            Optional.of("Kroflex"),
            thirdTime,
            "GET /downloads/games/minecraft HTTP/1.1",
            "minecraft",
            404,
            336,
            Optional.empty(),
            "Debian APT-HTTP/1.3 (0.9.7.9)"
        );
        return Stream.of(
            arguments(firstLog, firstExcept),
            arguments(secondLog, secondExcept),
            arguments(thirdLog, thirdExcept)
        );
    }

    static Stream<Arguments> invalidLogs() {
        String firstLog =
            "- - [17/May/2015:08:05:32 +0000]  304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        String secondLog =
            "216.46.173.126 - [26/May/2015:19:05:02 +0200] \"GET /downloads/product_1 HTTP/1.1\" 404 331 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"";

        String thirdLog =
            " \"93.180.71.3 - - [45/May/2015:08:05:32 +0000] \\\"GET /downloads/product_1 HTTP/1.1\\\" 304 0 \\\"-\\\" \\\"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\\\"\";";

        return Stream.of(
            arguments(firstLog),
            arguments(secondLog),
            arguments(thirdLog)
        );
    }

    @ParameterizedTest
    @MethodSource("logs")
    void testNGINXLogParser(String log, LogInf except) {
        LogInf result = NGINXLogParser.convertToLogInformation(log).get();
        assertEquals(result, except);
    }

    @ParameterizedTest
    @MethodSource("invalidLogs")
    void testTryParseInvalidLogs(String invalidLog) {
        Optional<LogInf> result = NGINXLogParser.convertToLogInformation(invalidLog);
        assertTrue(result.isEmpty());
    }

    private static OffsetDateTime convertToTime(String time, ZoneOffset zoneOffset) {
        LocalDateTime localTime =
            LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MMMM/yyyy:HH:mm:ss", Locale.ENGLISH));
        return localTime.atOffset(zoneOffset);
    }
}
