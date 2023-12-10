package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Horseshoe implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = getRootOfRadius(point);
        return new Point(1 / r * (point.x() - point.y()) * (point.x()) + point.y(), 1 / r * 2 * point.x() * point.y());
    }
}
