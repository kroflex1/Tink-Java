package edu.project4.Transformations;

import java.util.concurrent.ThreadLocalRandom;

public record Coefficients(double a, double b, double c, double d, double e, double f) {
    public static Coefficients createRandomCoefficients(){
        return new Coefficients(
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1),
            ThreadLocalRandom.current().nextDouble(-1, 1));
    }
}
