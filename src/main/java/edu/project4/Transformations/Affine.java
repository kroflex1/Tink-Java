package edu.project4.Transformations;

import edu.project4.Point;

public class Affine implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coeff) {
        return new Point(
            point.x() * coeff.a() + point.y() * coeff.b() + coeff.c(),
            point.x() * coeff.d() + point.y() * coeff.e() + coeff.f()
        );
    }
}
