package edu.project3.LogStatitic;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record LogStatistics(List<String> files, Optional<OffsetDateTime> from, Optional<OffsetDateTime> to,
                            long numberOfRequests, long averageResponseSizeInBytes,
                            Map<String, Long> numberOfCertainResources,
                            Map<ResponseCode, Long> responseCodes) {

}
