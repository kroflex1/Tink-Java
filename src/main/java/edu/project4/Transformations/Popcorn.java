package edu.project4.Transformations;

import edu.project4.Point;

public class Popcorn extends Transformation {

    public Popcorn(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        return new Point(
            point.x() + coeff.c() * Math.sin(Math.tan(3 * point.y())),
            point.x() + coeff.f() * Math.sin(Math.tan(3 * point.x()))
        );
    }
}
