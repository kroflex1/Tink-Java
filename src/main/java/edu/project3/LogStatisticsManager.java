package edu.project3;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class LogStatisticsManager {

    private static final Map<Integer, ResponseCode> RESPONSE_CODES = new HashMap<>() {{
        put(202, new ResponseCode(202, "Accepted"));
        put(502, new ResponseCode(502, "Bad Gateway"));
        put(405, new ResponseCode(405, "Bad  Method Not Allowed"));
        put(400, new ResponseCode(400, "Bad Request"));
        put(408, new ResponseCode(408, "Request Time-Out"));
        put(409, new ResponseCode(409, "Conflict"));
        put(201, new ResponseCode(201, "Created"));
        put(413, new ResponseCode(413, "Request Entity Too Large"));
        put(403, new ResponseCode(403, "Forbidden"));
        put(504, new ResponseCode(504, "Gateway Timeout"));
        put(410, new ResponseCode(410, "Gone"));
        put(500, new ResponseCode(500, "Internal Server Error"));
        put(411, new ResponseCode(411, "Length Required"));
        put(200, new ResponseCode(200, "OK"));
    }};

    public static LogStatistics collectStatistics(
        List<LogInf> logs,
        List<String> files,
        @Nullable OffsetDateTime from,
        @Nullable OffsetDateTime to
    ) {
        long numberOfRequests = 0;
        long sumOfBytes = 0;
        Map<String, Long> numberOfCertainResources = new HashMap<>();
        Map<ResponseCode, Long> responseCodes = new HashMap<>();
        for (LogInf currentLog : logs) {
            if ((from == null || currentLog.timeLocal().isAfter(from)) &&
                (to == null || currentLog.timeLocal().isBefore(to))) {
                numberOfRequests++;
                sumOfBytes += currentLog.bodyBytesSent();
                numberOfCertainResources.putIfAbsent(currentLog.resource(), 0L);
                numberOfCertainResources.put(
                    currentLog.resource(),
                    numberOfCertainResources.get(currentLog.resource()) + 1
                );
                ResponseCode currentResponseCode =
                    RESPONSE_CODES.containsKey(currentLog.status()) ? RESPONSE_CODES.get(currentLog.status()) :
                        new ResponseCode(currentLog.status(), "N/A");
                responseCodes.putIfAbsent(currentResponseCode, 0L);
                responseCodes.put(currentResponseCode, responseCodes.get(currentResponseCode) + 1);
            }
        }
        return new LogStatistics(
            files,
            from == null ? Optional.empty() : Optional.of(from),
            to == null ? Optional.empty() : Optional.of(to),
            numberOfRequests,
            sumOfBytes / numberOfRequests,
            numberOfCertainResources,
            responseCodes
        );
    }
}
