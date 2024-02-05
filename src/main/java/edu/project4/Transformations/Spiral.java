package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Spiral implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = getRootOfRadius(point);
        double theta = getTheta(point);
        return new Point(1 / r * (Math.cos(theta) + Math.sin(r)), 1 / r * (Math.sin(theta) - Math.cos(r)));
    }
}
