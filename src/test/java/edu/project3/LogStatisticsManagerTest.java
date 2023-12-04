package edu.project3;

import edu.project3.LogStatitic.LogInf;
import edu.project3.LogStatitic.LogStatistics;
import edu.project3.LogStatitic.LogStatisticsManager;
import edu.project3.LogStatitic.ResponseCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogStatisticsManagerTest {
    static Stream<Arguments> logs() {
        List<LogInf> firstLogs = List.of(
            new LogInf("1.1.1.1", Optional.of("Kroflex"),
                convertToTime("17/May/2015:08:05:32", ZoneOffset.of("+00:00")),
                "GET /downloads/product_2  HTTP/1.1", "product_2",
                200,
                100, Optional.empty(), "Debian"
            ),
            new LogInf("1.1.1.1", Optional.of("Kroflex"),
                convertToTime("18/May/2015:08:05:32", ZoneOffset.of("+00:00")),
                "GET /downloads/product_1  HTTP/1.1", "product_1",
                502,
                200, Optional.empty(), "Debian"
            ),
            new LogInf("1.1.1.1", Optional.of("Kroflex"),
                convertToTime("19/May/2015:08:05:32", ZoneOffset.of("+00:00")),
                "GET /downloads/product_2  HTTP/1.1", "product_2",
                200,
                100, Optional.empty(), "Debian"
            )
        );
        Map<String, Long> firstNumberOfCertainResources = new HashMap<>() {{
            put("product_1", 1L);
            put("product_2", 2L);
        }};
        Map<ResponseCode, Long> firstResponseCodes = new HashMap<>() {{
            put(new ResponseCode(200, "OK"), 2L);
            put(new ResponseCode(502, "Bad Gateway"), 1L);
        }};

        LogStatistics firstLogStatistics = new LogStatistics(List.of("access.log"),
            Optional.empty(),
            Optional.empty(),
            firstLogs.size(),
            (100 * 2 + 200) / firstLogs.size(),
            firstNumberOfCertainResources,
            firstResponseCodes);

        return Stream.of(
            arguments(firstLogs, List.of("access.log") ,firstLogStatistics)
        );
    }

    @ParameterizedTest
    @MethodSource("logs")
    void testCollectingStatistics(List<LogInf> logs, List<String> files,LogStatistics except) {
        LogStatistics result = LogStatisticsManager.collectStatistics(logs, files,null, null);
        assertEquals(result, except);
    }

    private static OffsetDateTime convertToTime(String time, ZoneOffset zoneOffset) {
        LocalDateTime localTime =
            LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MMMM/yyyy:HH:mm:ss", Locale.ENGLISH));
        return localTime.atOffset(zoneOffset);
    }
}
