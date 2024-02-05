package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Popcorn implements Transformation {
    private final static double MULTIPLIER = 3;

    @Override
    public Point apply(Point point, Coefficients coefficients) {
        return new Point(
            point.x() + coefficients.c() * Math.sin(Math.tan(MULTIPLIER * point.y())),
            point.x() + coefficients.f() * Math.sin(Math.tan(MULTIPLIER * point.x()))
        );
    }
}