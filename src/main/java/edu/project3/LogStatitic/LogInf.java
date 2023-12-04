package edu.project3.LogStatitic;

import java.time.OffsetDateTime;
import java.util.Optional;

public record LogInf(String remoteAddress, Optional<String> remoteUser, OffsetDateTime timeLocal,
                     String request, String resource, int status,
                     int bodyBytesSent, Optional<String> httpReferer, String httpUserAgent) {

}
