package edu.project3;

import edu.project3.LogStatitic.LogInf;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NGINXLogParser {

    private static final Pattern PATTERN_FOR_LOG =
        Pattern.compile(
            "([\\d.]+) - ([^ ]+) \\[([A-Za-z0-9/:]+) \\+(\\d{4})] \"(.+)\" (\\d{3}) (\\d+) \"(.+)\" \"(.+)\"");
    private static final int REMOTE_ADDRESS_GROUP = 1;
    private static final int REMOTE_USER_GROUP = 2;
    private static final int TIME_GROUP = 3;
    private static final int ZONE_OFFSET_GROUP = 4;
    private static final int REQUEST_GROUP = 5;
    private static final int STATUS_GROUP = 6;
    private static final int BODY_BYTES_SENT_GROUP = 7;
    private static final int HTTP_REFERER_GROUP = 8;
    private static final int HTTP_USER_AGENT_GROUP = 9;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        "dd/MMMM/yyyy:HH:mm:ss",
        Locale.ENGLISH
    );

    public static Optional<LogInf> convertToLogInformation(String line) {
        Matcher matcher = PATTERN_FOR_LOG.matcher(line);
        if (matcher.matches()) {
            OffsetDateTime localTime;
            try {
                localTime = convertStringToOffsetDateTime(matcher.group(TIME_GROUP), matcher.group(ZONE_OFFSET_GROUP));
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
            return Optional.of(new LogInf(
                matcher.group(REMOTE_ADDRESS_GROUP),
                matcher.group(REMOTE_USER_GROUP).equals("-") ? Optional.empty()
                    : Optional.of(matcher.group(REMOTE_USER_GROUP)),
                localTime,
                matcher.group(REQUEST_GROUP),
                getResourceFromRequest(matcher.group(REQUEST_GROUP)),
                Integer.parseInt(matcher.group(STATUS_GROUP)),
                Integer.parseInt(matcher.group(BODY_BYTES_SENT_GROUP)),
                matcher.group(HTTP_REFERER_GROUP).equals("-") ? Optional.empty()
                    : Optional.of(matcher.group(HTTP_REFERER_GROUP)),
                matcher.group(HTTP_USER_AGENT_GROUP)
            ));
        }
        return Optional.empty();
    }

    @SuppressWarnings("MagicNumber")
    private static OffsetDateTime convertStringToOffsetDateTime(String time, String zoneOffset) throws
        DateTimeParseException {
        LocalDateTime localTime = LocalDateTime.parse(time, DATE_TIME_FORMATTER);
        ZoneOffset zoneOffSet =
            ZoneOffset.of(String.format("+%s:%s", zoneOffset.substring(0, 2), zoneOffset.substring(2, 4)));
        return localTime.atOffset(zoneOffSet);
    }

    private static String getResourceFromRequest(String request) {
        Pattern pattern = Pattern.compile("/([^ ]+)");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            String[] parts = matcher.group(1).split("/");
            return parts[parts.length - 1];
        }
        return "";
    }

    private NGINXLogParser() {

    }
}
