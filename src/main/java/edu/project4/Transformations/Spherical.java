package edu.project4.Transformations;

import edu.project4.Point;

public class Spherical implements Transformation {

    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(1 / r * point.x(), 1 / r * point.y());
    }
}
