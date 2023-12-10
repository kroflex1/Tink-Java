package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Waves implements Transformation {

    @Override
    public Point apply(Point point, Coefficients coefficients) {
        return new Point(
            point.x() + coefficients.b() * Math.sin(point.y()) / Math.pow(coefficients.c(), 2),
            point.y() + coefficients.e() * Math.sin(point.x()) / Math.pow(coefficients.f(), 2)
        );
    }
}
