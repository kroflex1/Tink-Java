package edu.project4.Transformations;

import edu.project4.Point;

public class Diemond extends Transformation {
    public Diemond(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double theta = Math.atan2(point.y(), point.x());
        return new Point(Math.sin(theta) * Math.cos(r), Math.cos(theta) * Math.sin(r));
    }
}
