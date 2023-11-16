package edu.hw6;

import edu.hw6.Task3.SizeFilter;
import edu.hw6.Task5.HackerNews;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task5Test {

    static Stream<Arguments> topStoriesTitles() {
        return Stream.of(
            arguments(37570037L, "JDK 21 Release Notes"),
            arguments(38291399L, "Ransomware Group Files SEC Complaint over Victim's Failure Disclose Data Breach"),
            arguments(38282068L, "Archaeologists discover previously unknown ancient language"),
            arguments(38264742L, "Ask HN: Why, during the year 2023, does iOS still autocorrect .com to .con?")
        );
    }

    @Test
    void testGetTopStories() {
        long[] result = HackerNews.hackerNewsTopStories();
        assertTrue(result.length != 0);
    }

    @ParameterizedTest
    @MethodSource("topStoriesTitles")
    void testGetStoryTitle(long id, String exceptTitle) {
        String title = HackerNews.news(id).get();
        assertEquals(title, exceptTitle);
    }
}
