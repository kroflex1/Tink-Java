package edu.project4.Transformations;

import edu.project4.Point;

public class Exponential extends Transformation{
    public Exponential(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        return new Point(Math.exp(point.x()-1) * Math.cos(Math.PI * point.y()),
                        Math.exp(point.x()-1) * Math.sin(Math.PI * point.y()));
    }
}
