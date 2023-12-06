package edu.project4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RectTest {
    @ParameterizedTest
    @MethodSource("pointsInsideRect")
    void testRectContainsPoint(Rect rect, List<Point> insidePoints) {
        for (Point currentPoint : insidePoints) {
            assertTrue(rect.contains(currentPoint));
        }
    }

    @ParameterizedTest
    @MethodSource("pointsOutsideRect")
    void testRectNotContainsPoint(Rect rect, List<Point> outsidePoints) {
        for (Point currentPoint : outsidePoints) {
            assertFalse(rect.contains(currentPoint));
        }
    }

    @ParameterizedTest
    @MethodSource("randomPointsFromRect")
    void testGetRandomPointFromRect(Rect rect, Point randomPointInsideRect) {
        assertTrue(rect.contains(randomPointInsideRect));
    }

    private static Stream<Arguments> pointsInsideRect() {
        Rect firstRect = new Rect(0, 0, 10, 10);
        List<Point> pointsForFirstRect = List.of(
            new Point(0, 0),
            new Point(10, 10),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0.8, 5),
            new Point(0, 5),
            new Point(6.7, 8.3)
        );

        Rect secondRect = new Rect(30, 60, 10, 10);
        List<Point> pointsForSecondRect = List.of(
            new Point(30, 60),
            new Point(40, 70),
            new Point(35, 62),
            new Point(38.2, 68.3),
            new Point(30, 62),
            new Point(35, 70)
        );
        return Stream.of(
            Arguments.of(firstRect, pointsForFirstRect),
            Arguments.of(secondRect, pointsForSecondRect)
        );
    }

    private static Stream<Arguments> pointsOutsideRect() {
        Rect firstRect = new Rect(0, 0, 10, 10);
        List<Point> pointsOutsideFirstRect = List.of(
            new Point(-1, -1),
            new Point(11, 11),
            new Point(0, 11),
            new Point(11, 0),
            new Point(12, 5),
            new Point(5, -8)
        );

        Rect secondRect = new Rect(30, 60, 10, 10);
        List<Point> pointsOutsideSecondRect = List.of(
            new Point(10, 10),
            new Point(42, 65),
            new Point(35, 45),
            new Point(38.2, 59.9),
            new Point(42, 62)
        );
        return Stream.of(
            Arguments.of(firstRect, pointsOutsideFirstRect),
            Arguments.of(secondRect, pointsOutsideSecondRect)
        );
    }

    private static Stream<Arguments> randomPointsFromRect() {
        Rect firstRect = new Rect(0, 0, 100, 100);
        List<Point> randomPointsForFirstRect = Stream.generate(firstRect::getRandomPoint).limit(50).toList();
        Rect secondRect = new Rect(50.7, 125.3, 15.3, 85.3);
        List<Point> randomPointsForSecondRect = Stream.generate(secondRect::getRandomPoint).limit(50).toList();

        List<Arguments> arguments = new ArrayList<>();
        for (Point currentPoint : randomPointsForFirstRect) {
            arguments.add(Arguments.of(firstRect, currentPoint));
        }
        for (Point currentPoint : randomPointsForSecondRect) {
            arguments.add(Arguments.of(secondRect, currentPoint));
        }
        return arguments.stream();
    }

}
