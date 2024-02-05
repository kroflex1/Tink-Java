package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Handkerchief implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = getRootOfRadius(point);
        double theta = getTheta(point);
        return new Point(r * Math.sin(theta + r), r * Math.cos(theta - r));
    }
}
