package edu.project4.Records;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public record Coefficients(double a, double b, double c, double d, double e, double f, Color color) {
    public static Coefficients createRandomCoefficients() {
        return new Coefficients(
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            new Color(
                ThreadLocalRandom.current().nextInt(1, 256),
                ThreadLocalRandom.current().nextInt(1, 256),
                ThreadLocalRandom.current().nextInt(1, 256)
            )
        );

    }
}
