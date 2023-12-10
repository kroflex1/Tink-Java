package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Swirl implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = getRadius(point);
        double sinR = Math.sin(r);
        double cosR = Math.cos(r);
        return new Point(point.x() * sinR - point.y() * cosR, point.x() * cosR + point.y() * sinR);
    }
}
