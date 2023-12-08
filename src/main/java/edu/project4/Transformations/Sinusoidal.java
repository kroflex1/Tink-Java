package edu.project4.Transformations;

import edu.project4.Point;

public class Sinusoidal implements Transformation {

    @Override
    public Point apply(Point point, Coefficients coefficients) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
