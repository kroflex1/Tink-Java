package edu.project4;

import edu.project4.Transformations.Coefficients;
import edu.project4.Transformations.Transformation;
import java.util.concurrent.ThreadLocalRandom;

public record Function(Transformation transformation, Coefficients coeff, double weight, int red, int green, int blue) {

    public Function(Transformation transformation, Coefficients coeff) {
        this(
            transformation,
            coeff,
            1,
            ThreadLocalRandom.current().nextInt(1, 256),
            ThreadLocalRandom.current().nextInt(1, 256),
            ThreadLocalRandom.current().nextInt(1, 256)
        );
    }

    public Function(Transformation transformation, Coefficients coeff, double weight) {
        this(
            transformation,
            coeff,
            weight,
            ThreadLocalRandom.current().nextInt(1, 256),
            ThreadLocalRandom.current().nextInt(1, 256),
            ThreadLocalRandom.current().nextInt(1, 256)
        );
    }

    public Point apply(Point point) {
        Point newPoint = new Point(
            point.x() * coeff.a() + point.y() * coeff.b() + coeff.c(),
            point.x() * coeff.d() + point.y() * coeff.e() + coeff.f()
        );
        newPoint = transformation.apply(newPoint);
        return new Point(newPoint.x() * weight, newPoint.y() * weight);
    }
}
