package edu.project4.Transformations;

import edu.project4.Point;

public class Waves extends Transformation {
    public Waves(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        return new Point(
            point.x() + coeff.b() * Math.sin(point.y()) / Math.pow(coeff.c(), 2),
            point.y() + coeff.e() * Math.sin(point.x()) / Math.pow(coeff.f(), 2)
        );
    }
}
