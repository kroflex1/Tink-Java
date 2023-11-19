package edu.project3;

import java.time.OffsetDateTime;
import java.util.Optional;

public record LogRecord(String remoteAddress, Optional<String> remoteUser, OffsetDateTime timeLocal,
                        String request, int status,
                        int bodyBytesSent, Optional<String> httpReferer, String httpUserAgent) {

}
