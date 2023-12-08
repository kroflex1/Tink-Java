package edu.project4.Transformations;

import edu.project4.Point;

public class Sinusoidal extends Transformation {
    public Sinusoidal(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
