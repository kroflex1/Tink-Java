package edu.project4.Records;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public record Coefficients(double a, double b, double c, double d, double e, double f, Color color) {
    private static final int MIN_COLOR_VALUE = 0;
    private static final int MAX_COLOR_VALUE = 255;

    public static Coefficients createRandomCoefficients() {
        return new Coefficients(
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            new Color(
                ThreadLocalRandom.current().nextInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE + 1),
                ThreadLocalRandom.current().nextInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE + 1),
                ThreadLocalRandom.current().nextInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE + 1)
            )
        );

    }
}
