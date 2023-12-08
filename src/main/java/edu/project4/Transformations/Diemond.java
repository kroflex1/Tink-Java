package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Diemond implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double theta = Math.atan2(point.y(), point.x());
        return new Point(Math.sin(theta) * Math.cos(r), Math.cos(theta) * Math.sin(r));
    }
}
